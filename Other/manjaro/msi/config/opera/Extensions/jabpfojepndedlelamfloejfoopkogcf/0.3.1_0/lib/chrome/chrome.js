var app = {};

app.version = function () {return chrome.runtime.getManifest().version};
app.homepage = function () {return chrome.runtime.getManifest().homepage_url};
app.tab = {"open": function (url) {chrome.tabs.create({"url": url, "active": true})}};
chrome.runtime.onMessage.addListener(function (e, s, r) {if (e.message === "top") r(s.tab.url)});
chrome.runtime.setUninstallURL(app.homepage() + "?v=" + app.version() + "&type=uninstall", function () {});

chrome.runtime.onInstalled.addListener(function (e) {
  window.setTimeout(function () {
    if (e.reason === "install") {
      app.tab.open(app.homepage() + "?v=" + app.version() + "&type=" + e.reason);
      config.welcome.lastupdate = Date.now();
    }
  }, 3000);
});

app.button = (function () {
  var onCommand;
  chrome.browserAction.onClicked.addListener(function () {if (onCommand) onCommand()});
  /*  */
  return {
    "onCommand": function (c) {onCommand = c},
    set icon (o) {chrome.browserAction.setIcon(o)},
    set label (label) {chrome.browserAction.setTitle({"title": label})}
  };
})();

app.storage = (function () {
  var objs = {};
  chrome.storage.onChanged.addListener(function () {chrome.storage.local.get(null, function (o) {objs = o})});
  window.setTimeout(function () {
    chrome.storage.local.get(null, function (o) {
      objs = o;
      var script = document.createElement("script");
      script.src = "../common.js";
      document.body.appendChild(script);
    });
  }, 300);
  /*  */
  return {
    "read": function (id) {return objs[id]},
    "write": function (id, data) {
      var tmp = {};
      tmp[id] = data;
      objs[id] = data;
      chrome.storage.local.set(tmp, function () {});
    }
  }
})();
