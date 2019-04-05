;(function (m) {
    'use strict';

    chrome.storage.local.get(function (storage) {

        var tabs = {}, // to-be module
            tabGroups = storage.tabGroups || [], // tab groups
            opts = storage.options || {
                deleteTabOnOpen: 'no'
            };

        function saveTabGroups(json) {
            chrome.storage.local.set({ tabGroups: json });
        }

        function updateTotalTabs() {
            var totalTabs = 0;
            for (var i = 0; i < storage.tabGroups.length; i+=1) {
                totalTabs += storage.tabGroups[i].tabs.length;
            }
            document.getElementById('totalTabs').innerHTML = totalTabs == 1 ? totalTabs + ' tab' : totalTabs + ' tabs';
        }

        // model entity
        // 'data' is meant to be a tab group object from localStorage
        tabs.TabGroup = function (data) {
            this.date = m.prop(data.date);
            this.id   = m.prop(data.id);
            this.tabs = m.prop(data.tabs);
            this.title = m.prop(data.title);
        };

        // alias for Array
        tabs.TabGroupsList = Array;

        // view-model
        tabs.vm = new function () {
            var vm = {};
            vm.init = function () {
                // list of tab groups
                vm.list = new tabs.TabGroupsList();

                vm.removeGroup = function (i) {
                    // remove view from array
                    vm.list.splice(i, 1);
                    // remove from localStorage
                    tabGroups.splice(i, 1)
                    // save
                    saveTabGroups(tabGroups);
                    updateTotalTabs();
                };

                vm.renameGroup = function (i, title) {
                    tabGroups[i].title = title;
                    saveTabGroups(tabGroups);
                };

                vm.removeTab = function (i, ii) {
                    // remove from view array
                    //vm.list[i].tabs().splice(ii, 1);
                    // remove from localStorage
                    tabGroups[i].tabs.splice(ii, 1);

                    if (tabGroups[i].tabs.length == 0) {
                        tabs.vm.removeGroup(i);
                    }
                    // save
                    saveTabGroups(tabGroups);
                    updateTotalTabs();
                };
            };
            return vm;
        };

        tabs.controller = function () {
            var i;
            tabs.vm.init();
            for (i = 0; i < tabGroups.length; i += 1) {
                tabs.vm.list.push(new tabs.TabGroup(tabGroups[i]));
            }
        };

        tabs.view = function () {
            if (tabs.vm.list.length === 0) {
                return m('p', 'No tab groups have been saved yet, or you deleted them all.');
            }

            // foreach tab group
            return tabs.vm.list.map(function (group, i) {

                // group
                return m('div.group', [
                    m('div.group__header', [
                        m('input.group__title', {
                            value: storage.tabGroups[i].title ? storage.tabGroups[i].title : '',
                            placeholder: 'Type',
                            //style: storage.tabGroups[i].title ? '' : 'display: none',
                            onclick: function () {
                                autoSize(this);
                            },
                            onblur: function () {
                                tabs.vm.renameGroup(i, this.value);
                                this.value ? this.style.display = 'block' : this.style.display = 'none';
                                autoSize(this);
                            },
                            'data-id': storage.tabGroups[i].id
                        }),
                        m('div.group__amount', group.tabs().length > 1 ? group.tabs().length + ' tabs' : group.tabs().length + ' tab'),
                        m('span.group__action.group__action_restore', { onclick: function () {
                            var i;

                            // reason this goes before opening the tabs and not
                            // after is because it doesn't work otherwise
                            // I imagine it's because you changed tab and so
                            // that messes with the focus of the JS somehow...
                            if (opts.deleteTabOnOpen === 'yes') {
                                tabs.vm.removeGroup(i);
                            }

                            for (i = 0; i < group.tabs().length; i += 1) {
                                chrome.tabs.create({
                                    url: group.tabs()[i].url,
                                    pinned: group.tabs()[i].pinned
                                });
                            }
                        } }, 'Restore group as tabs'),
                        m('span.group__action.group__action_delete', { onclick: function () {
                            tabs.vm.removeGroup(i);
                        } }, 'Delete group'),
                        m('span.group__action.group__action_rename', { onclick: function () {
                            var titleInput = this.parentElement.getElementsByClassName('group__title')[0];
                            titleInput.style.display = 'block';
                            titleInput.focus();
                            autoSize(titleInput);
                            //tabs.vm.renameGroup(i);
                        } }, 'Rename'),
                        m('span.group__date', 'Created ' + moment(group.date()).format('DD/MM/YYYY, HH:mm:ss')),
                    ]),

                    // foreach tab
                    m('ul.links', group.tabs().map(function (tab, ii) {
                        return m('li.links__item', {draggable: true}, [
                            m('span.links__item-remove', { onclick: function () {
                                tabs.vm.removeTab(i, ii);
                            } }),
                            m('img.links__item-icon', { src: tab.favIconUrl, height: '16', width: '16' }),
                            ' ',
                            m('span.links__item-link', { onclick: function () {
                                if (opts.deleteTabOnOpen === 'yes') {
                                    tabs.vm.removeTab(i, ii);
                                }

                                chrome.tabs.create({
                                    url: tab.url,
                                    pinned: tab.pinned
                                });
                            } }, tab.title)
                        ]);
                    }))
                ]);
            });
        };

        // init the app
        m.module(document.getElementById('groups'), { controller: tabs.controller, view: tabs.view });

        var titleInputs = document.getElementsByClassName('group__title');
        for (var i = 0; i < titleInputs.length; i += 1) {
            autoSize(titleInputs[i]);
        }

        updateTotalTabs();

        var linkLists = document.getElementsByClassName('links');

        var sortable = [];
        for (i=0; i < linkLists.length; i++) {
            sortable[i] = new Sortable(linkLists[i], {
                sort: true,  // sorting inside list
                delay: 0, // time in milliseconds to define when the sorting should start
                animation: 150,  // ms, animation speed moving items when sorting, `0` — without animation
                filter: ".ignore-elements",  // Selectors that do not lead to dragging (String or Function)
                draggable: ".links__item",  // Specifies which items inside the element should be draggable
                ghostClass: "sortable-ghost",  // Class name for the drop placeholder
                chosenClass: "sortable-chosen",  // Class name for the chosen item
                onEnd: function ( evt ) {
                    var groupIndex = sortable.indexOf(this);
                    arrayMove(tabGroups[groupIndex].tabs, evt.oldIndex, evt.newIndex);
                    saveTabGroups(tabGroups);
                },
            });
        }

        var titles = document.getElementsByClassName('group__title');

        for (i=0; i<titles.length; i++) {
            if (!titles[i].value) {
                titles[i].style.display = 'none';
            }
        }

    });

    function autoSize(input) {

        var id = input.dataset.id;
        var canvas = document.getElementById( id );

        if (!canvas) {
            canvas = document.createElement('canvas');
            canvas.setAttribute('id', id);
            canvas.setAttribute('style', 'position: absolute; left: -9999px');
            document.body.appendChild(canvas);
        }

        var ctx = canvas.getContext('2d');

        ctx.font = getComputedStyle(input,null).getPropertyValue('font');
        input.style.width = ctx.measureText(input.value + '  ').width + 'px';

        input.addEventListener('input', function () {
            ctx.font = getComputedStyle(this,null).getPropertyValue('font');
            this.style.width = ctx.measureText(this.value + '  ').width + 'px';
        });
    }

    document.getElementById('saveAll').addEventListener('click', function () {
        chrome.tabs.query({ currentWindow: true }, function (tabsArr) {
            chrome.runtime.sendMessage({ action: 'save', tabsArr: tabsArr }, function (res) {
                if (res === 'ok') {
                    window.close();
                }
            });
        });
    });

    function arrayMove(arr, fromIndex, toIndex) {
        var element = arr[fromIndex];
        arr.splice(fromIndex, 1);
        arr.splice(toIndex, 0, element);
    }

}(m));
