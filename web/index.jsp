<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/includes/header.html"/>
        <div class="container">
            <div class="header"></div>
            <script src="./js/typewriting.js"charset="utf-8">
            </script>
            <script charset="utf-8">
                var typeWriting = new TypeWriting({
                targetElement   : document.getElementsByClassName('header')[0],
                inputString     : '> Hello Friend.',
                typing_interval : 130, // Interval between each character
                blink_interval  : '1s', // Interval of the cursor blinks
                cursor_color    : '#00fd55', // Color of the cursor
                }, function() { console.log("END"); });
            </script>
            <form class="search-bar" action="https://duckduckgo.com/" >
                <input autofocus type="search" name="q" placeholder="Search in DuckDuckGo" autocomplete="off">
            </form>
            <div class="terminal-container">
                <div class="bookmark-container">
                <form class="home" action="bookmarks" method="post">
                    <input type="hidden" name="action" value="view">
                    <input class="myButton" type="Submit" value="View Bookmarks">
                </form>
                </div>

            </div>
        </div>
<c:import url="/includes/footer.jsp"/>
