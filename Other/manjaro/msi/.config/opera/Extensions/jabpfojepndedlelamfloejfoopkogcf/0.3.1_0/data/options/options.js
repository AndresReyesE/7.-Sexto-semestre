var config = {
  "elements": {},
  "themes": {"number": 38},
  "custom": {
    "twitch": "\:\/\/(www\.)?twitch\.tv",
    "ebay": "\:\/\/(www\.)?ebay\.([a-zA-Z]+)",
    "bing": "\:\/\/(www\.)?bing\.([a-zA-Z]+)",
    "wikipedia": "\:\/\/(www\.)?wikipedia\.org",
    "reddit": "\:\/\/(www\.)?reddit\.([a-zA-Z]+)",
    "amazon": "\:\/\/(www\.)?amazon\.([a-zA-Z]+)",
    "github": "\:\/\/(www\.)?github\.([a-zA-Z]+)",
    "tumblr": "\:\/\/(www\.)?tumblr\.([a-zA-Z]+)",
    "youtube": "\:\/\/(www\.)?youtube\.([a-zA-Z]+)",
    "dropbox": "\:\/\/(www\.)?dropbox\.([a-zA-Z]+)",
    "twitter": "\:\/\/(www\.)?twitter\.([a-zA-Z]+)",
    "play": "\:\/\/(www\.)?play\.google\.([a-zA-Z]+)",
    "facebook": "\:\/\/(www\.)?facebook\.([a-zA-Z]+)",
    "maps": "\:\/\/(www\.)?google\.([a-zA-Z]+)\/maps",
    "docs": "\:\/\/(www\.)?docs\.google\.([a-zA-Z]+)",
    "yahoo": "\:\/\/([a-zA-Z.]*\.)?yahoo\.([a-zA-Z]+)",
    "gmail": "\:\/\/(www\.)?mail\.google\.([a-zA-Z]+)",
    "inbox": "\:\/\/(www\.)?inbox\.google\.([a-zA-Z]+)",
    "drive": "\:\/\/(www\.)?drive\.google\.([a-zA-Z]+)",
    "sites": "\:\/\/(www\.)?sites\.google\.([a-zA-Z]+)",
    "instagram": "\:\/\/(www\.)?instagram\.([a-zA-Z]+)",
    "w3schools": "\:\/\/(www\.)?w3schools\.([a-zA-Z]+)",
    "google": "\:\/\/(www\.)?google\.([a-zA-Z]+)\/search",
    "duckduckgo": "\:\/\/(www\.)?duckduckgo\.([a-zA-Z]+)",
    "accounts": "\:\/\/(www\.)?accounts\.google\.([a-zA-Z]+)",
    "stackoverflow": "\:\/\/(www\.)?stackoverflow\.([a-zA-Z]+)",
    "translate": "\:\/\/(www\.)?translate\.google\.([a-zA-Z]+)"
  },
  "host": {
    "name": function (url) {
      url = url.replace("www.", '');
      var s = url.indexOf("//") + 2;
      if (s > 1) {
        var o = url.indexOf('/', s);
        if (o > 0) return url.substring(s, o);
        else {
          o = url.indexOf('?', s);
          if (o > 0) return url.substring(s, o);
          else return url.substring(s);
        }
      } else return url;
    }
  },
  "click": {
    "handle": function () {
      var id = this.id;
      var checked = this.checked;
      /*  */
      if (id.indexOf("dark_") !== -1) {
        for (var i = 1; i <= config.themes.number; i++) document.getElementById("dark_" + i).checked = false;
        document.getElementById(id).checked = checked;
      }
      /*  */
      var tmp = {};
      for (var name in config.custom) tmp[name] = document.getElementById(name).checked;
      for (var i = 1; i <= config.themes.number; i++) {
        var name = "dark_" + i;
        tmp[name] = document.getElementById(name).checked;
      }
      chrome.storage.local.set(tmp, function () {});
    }
  },
  "update": function () {
    var tmp = {"whitelist": [], "state": "light", "custom": ''};
    var arr = [...document.querySelectorAll("input[type='checkbox']")];
    var color = {"normal": "rgb(140, 140, 140)", "highlight": "rgb(193, 193, 193)"};
    /*  */
    chrome.storage.local.get(tmp, function (e) {
      config.elements.custom.value = e.custom;
      config.elements.whitelist.value = e.whitelist.join(', ');
    });
    /*  */
    for (var i = 0; i < arr.length; i++) {
      var checkbox = arr[i];
      if (checkbox) {
        var label = checkbox.parentNode;
        if (label) {
          label.style.color = checkbox.checked ? color.highlight : color.normal;
        }
      }
    }
  },
  "load": function () {
    config.update();
    window.removeEventListener("load", config.load, false);
    /*  */
    for (var name in config.custom) document.getElementById(name).addEventListener("click", config.click.handle);
    for (var i = 1; i <= config.themes.number; i++) document.getElementById("dark_" + i).addEventListener("click", config.click.handle);
    config.elements.support.addEventListener("click", function () {chrome.storage.local.set({"support": this.checked}, function () {})});
    config.elements.custom.addEventListener("change", function () {chrome.storage.local.set({"custom": this.value}, function () {})});
    /*  */
    config.elements.whitelist.addEventListener("change", function () {
      var tmp = [];
      var whitelist = config.elements.whitelist.value || '';
      var hosts = whitelist.split(/\s*\,\s*/);
      for (var i = 0; i < hosts.length; i++) tmp.push(config.host.name(hosts[i]));
      tmp = tmp.filter(function (element, index, array) {return element && array.indexOf(element) === index});
      chrome.storage.local.set({"whitelist": tmp}, function () {});
    });
  },
  "contentloaded": function () {
    config.elements.custom = document.getElementById("custom");
    config.elements.support = document.getElementById("support");
    config.elements.whitelist = document.getElementById("whitelist");
    var firefox = navigator.userAgent.toLowerCase().indexOf("firefox") !== -1;
    document.removeEventListener("DOMContentLoaded", config.contentloaded, false);
    /*  */
    var tmp = {};
    for (var name in config.custom) tmp[name] = true;
    for (var i = 1; i <= config.themes.number; i++) tmp["dark_" + i] = false;
    tmp["dark_1"] = true;
    tmp["support"] = firefox ? false : true;
    /*  */
    chrome.storage.local.get(tmp, function (e) {
      config.elements.support.checked = e.support;
      for (var name in config.custom) document.getElementById(name).checked = e[name];
      for (var i = 1; i <= config.themes.number; i++) {
        var name = "dark_" + i;
        document.getElementById(name).checked = e[name];
      }
      /*  */
      config.update();
    });
  }
};

chrome.storage.onChanged.addListener(config.update);
window.addEventListener("load", config.load, false);
document.addEventListener("DOMContentLoaded", config.contentloaded, false);
