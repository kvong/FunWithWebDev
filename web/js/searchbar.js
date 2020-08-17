/* Code from github/grtcdr
 * Big thanks to u/Teiem1 from reddit for refactoring the old code!*/

/*A simple counter that is incremented when the search engines are cycled through*/
let se = 3;

/*Listens for click event in se_button, once clicked, se increments by one and cycleSearchEngines() is called to update the icon, placeholder, and form action*/
document.getElementById("search-icon-button").addEventListener("click", function() {
  se++;
  cycleSearchEngines(se);
});

const search_engines = [{
  src: "ddg.svg",
  placeholder: "DuckDuckGo",
  action: "https://www.duckduckgo.com/"
}, {
  src: "goog.svg",
  placeholder: "Google",
  action: "https://www.google.com/search?q="
},  {
  src: "reddit.svg",
  placeholder: "Reddit",
  action: "https://www.reddit.com/search?q="
},  {
  src: "youtube.svg",
  placeholder: "YouTube",
  action: "https://www.youtube.com/results?q="
}];

const cycleSearchEngines = se => {
  const curData = search_engines[(se+1) % search_engines.length];
  document.getElementById("search-icon").src = "./icons/" + curData.src;
  document.getElementById("search-input").placeholder = "Searching with " + curData.placeholder;
  document.getElementById("search-bar-form").action = curData.action;
  
};

