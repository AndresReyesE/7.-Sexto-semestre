;(function () {
    'use strict';

    // all tabs
    document.getElementById('save-all').addEventListener('click', function () {
        chrome.tabs.query({ currentWindow: true }, function (tabsArr) {
            chrome.runtime.sendMessage({ action: 'save', tabsArr: tabsArr }, function (res) {
                if (res === 'ok') {
                    window.close();
                }
            });
        });
    });

    // open background page
    document.getElementById('open-background-page').addEventListener('click', function () {
        chrome.tabs.create({ url: chrome.extension.getURL('grouptabs.html') });
    });

    chrome.storage.local.get('tabGroups', function (storage) {
        var totalTabs = 0;
        if (storage.tabGroups) {
            for (var i = 0; i < storage.tabGroups.length; i+=1) {
                totalTabs += storage.tabGroups[i].tabs.length;
            }
        }
        document.getElementById('savedTabsQty').innerHTML = totalTabs;
    });

    chrome.tabs.query({}, function(foundTabs) {
        var tabsCount = foundTabs.length;
        document.getElementById('openedTabsQty').innerHTML = tabsCount;
    });

}());
