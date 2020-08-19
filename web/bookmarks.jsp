<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/includes/header.html"/>
<script src="js/bookmarking.js"></script>
<div class="container">
    <div class="header" id="header" style="display: block"></div>
    <script src="./js/typewriting.js"charset="utf-8"></script>
    <script charset="utf-8">
        var typeWriting = new TypeWriting({
        targetElement   : document.getElementsByClassName('header')[0],
        inputString     : '> Bookmarks',
        typing_interval : 130, // Interval between each character
        blink_interval  : '1s', // Interval of the cursor blinks
        cursor_color    : '#00fd55', // Color of the cursor
        }, function() { console.log("END"); });
    </script>
    <div class="search-bar" id="search-bar">
        <form id="search-bar-form" method="get" action="https://www.duckduckgo.com">
        <button type="button" id="search-icon-button"><img id="search-icon" src="./icons/ddg.svg" style="filter: invert(1)"></button><input type="search" id="search-input" name="q" placeholder="Searching with DuckDuckGo" autocomplete="off" autofocus/><button id="go-button" type="submit">GO</button>
        </form>
    </div>
    <div class="terminal-container"  id="table" style="display: block">
        <div class="bookmark-container">

            <c:forEach items="${sections}" var="section">
                <c:if test = "${section.type != 'Update'}">
                    <div class="bookmark-set">
                        <div class="bookmark-title">${section.type}</div>
                        <div class="bookmark-inner-container">
                        <c:forEach items="${section.bookmarks}" var="bookmark">
                            <div class="bookmark"><a href="${bookmark.url}"><i class="${bookmark.icon}"></i> ${bookmark.name}</a></div>
                        </c:forEach>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <button class="myButton" onclick="showBookmarkOptions('bookmark-add')">Add Bookmark</button>
        <button class="myButton" onclick="showBookmarkOptions('bookmark-delete')">Delete Bookmark</button>
        <button class="myButton" onclick="showBookmarkOptions('bookmark-update')">Update Bookmark</button>

        <p class="errorMessage">${message}</p>
    </div>
    <div class="terminal-container2">
        <div class="bookmark-container">
    <div id="options" class="bookmarkForm" style="display: none">
        <div id="bookmark-add" style="display: none">
            <form class="home" action="bookmarks" method="post">
                <input type="hidden" name="action" value="add">
                <h3>Add Bookmark:</h3>
                <label for="type"><span class="required">*</span>Bookmark Type:</label><br>
                <input type="text" id="type" name="type"  maxlength="50" required><br>
                <label for="name"><span class="required">*</span>Bookmark Name:</label><br>
                <input type="text" id="name" name="name"  maxlength="50" required><br>
                <label for="url"><span class="required">*</span>Bookmark URL:</label><br>
                <input type="text" id="url" name="url"  maxlength="255" required><br>
                <label for="icon">FontAwesome Icon:</label><br>
                <input type="text" id="icon" name="icon" maxlength="50"><br>
                <label for="logo">PNG logo:</label><br>
                <input type="text" id="logo" name="logo" maxlength="50"><br>
                <input class="myButton" type="Submit" value="Submit">
                <button class="myButton" type="button" onclick="showBookmarkOptions('bookmark-add')">Cancel</button>
            </form>
        </div>
        <div id="bookmark-delete" style="display: none">
            <form class="home" action="bookmarks" method="post">
                <input type="hidden" name="action" value="delete">
                <h3>Delete Bookmark:</h3>
                <label for="name"><span class="required">*</span>Bookmark Name:</label><br>
                <input type="text" id="name" name="name" maxlength="50" required><br>
                <input class="myButton" type="Submit" value="Submit">
                <button class="myButton" type="button" onclick="showBookmarkOptions('bookmark-delete')">Cancel</button>

            </form>
        </div>
        <div id="bookmark-update" style="display: none">
            <form class="home" action="bookmarks" method="post">
                <input type="hidden" name="action" value="update">
                <h3>Update Bookmark:</h3>
                <label for="name"><span class="required">*</span>Bookmark Name:</label><br>
                <input type="text" id="name" name="name" maxlength="50" required><br>
                <label for="type">Update Type:</label><br>
                <input type="text" id="type" name="type" maxlength="50"><br>
                <label for="name">Update Name:</label><br>
                <input type="text" id="new-name" name="new-name" maxlength="50"><br>
                <label for="url">Update URL:</label><br>
                <input type="text" id="url" name="url" maxlength="255"><br>
                <label for="icon">Update Icon:</label><br>
                <input type="text" id="icon" name="icon" maxlength="50"><br>
                <label for="logo">Update logo:</label><br>
                <input type="text" id="logo" name="logo" maxlength="50"><br>
                <div>
                <input class="myButton" type="Submit" value="Submit">
                <button class="myButton" type="button" onclick="showBookmarkOptions('bookmark-update')">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>
    </div>
</div>
<c:import url="/includes/footer.jsp"/>
