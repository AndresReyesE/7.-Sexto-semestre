var config = {
  "themes": {"number": 38},
  "customized": {"number": 38},
  "tab": {"href": document.location.href},
  "link": document.getElementById("dark-mode"),
  "style": document.getElementById("dark-mode-custom-style"),
  "head": document.documentElement || document.head || document.querySelector("head"),
  "init": function (e) {
    if (e) config.tab.href = e;
    config.update();
  },
  "edit": function (href, txt) {
    config.style.textContent = txt;
    config.link.setAttribute("href", href);
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
  "load": function () {
    if (!config.link) {
      config.link = document.createElement("link");
      config.link.setAttribute("type", "text/css");
      config.link.setAttribute("id", "dark-mode");
      config.link.setAttribute("rel", "stylesheet");
      if (config.head) config.head.appendChild(config.link);
    }
    /*  */
    if (!config.style) {
      config.style = document.createElement("style");
      config.style.setAttribute("type", "text/css");
      config.style.setAttribute("id", "dark-mode-custom-style");
      if (config.head) config.head.appendChild(config.style);
    }
    /*  */
    var observer = new MutationObserver(function (e) {
      var tmp = document.getElementById("dark-mode");
      if (!tmp) {
        if (config.head) {
          config.head.appendChild(config.link);
        }
      }
      observer.disconnect();
    });
    /*  */
    observer.observe(document, {"childList": true, "subtree": true});
  },
  "update": function () {
    var tmp = {};
    for (var name in config.custom) tmp[name] = true;
    for (var i = 1; i <= config.themes.number; i++) tmp['dark_' + i] = false;
    /*  */
    tmp["dark_1"] = true;
    tmp["whitelist"] = [];
    tmp["state"] = "light";
    /*  */
    chrome.storage.local.get(tmp, function (e) {
      var id = null;
      var host = config.host.name(config.tab.href);
      /* disable dark mode for chrome newtab page */
      if (config.tab.href.indexOf("/chrome/newtab") !== -1) return config.edit('', '');
      for (var i = 0; i < e.whitelist.length; i++) {
        if (e.whitelist[i] === host) return config.edit('', '');
      }
      /*  */
      for (var i = 1; i <= config.themes.number; i++) {
        if (e['dark_' + i]) {
          id = i;
          break;
        }
      }
      /*  */
      for (var name in config.custom) {
        if (e[name]) {
          var rule = new RegExp(config.custom[name]);
          if (rule.test(config.tab.href)) {
            var href = e.state === "dark" ? chrome.runtime.getURL("data/content_script/custom/" + name + ".css") : '';
            config.edit(href, '');
            return;
          }
        }
      }
      /*  */
      if (e.state === "dark") {
        if (id) {
          var href = chrome.runtime.getURL('data/content_script/general/dark_' + id + '.css');
          if (id === config.customized.number) chrome.storage.local.get({"custom": ''}, function (e) {config.edit('', e.custom)});
          else config.edit(href, '');
        } else config.edit('', '');
      } else config.edit('', '');
    });
  }
};

if (window === window.top) config.update();
else chrome.runtime.sendMessage({"message": "top"}, config.init);

config.load();
chrome.storage.onChanged.addListener(config.update);
