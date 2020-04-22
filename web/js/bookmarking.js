function showBookmarkOptions(id) {
    if (id !== undefined){
        background = "options";
        table = "table";
        header = "header";
        otherId0 = "";
        otherId1 = "";
        switch (id){
            case "bookmark-add":
                otherId0 = "bookmark-delete";
                otherId1 = "bookmark-update";
                break;
            case "bookmark-delete":
                otherId0 = "bookmark-add";
                otherId1 = "bookmark-update";
                break;
            case "bookmark-update":
                otherId0 = "bookmark-add";
                otherId1 = "bookmark-delete";
                break;
        }
        
        var selectedOption = document.getElementById(id);
        var otherOption0 = document.getElementById(otherId0);
        var otherOption1 = document.getElementById(otherId1);
        var otherOption3 = document.getElementById(background);
        var otherOption4 = document.getElementById(table);
        var otherOption5 = document.getElementById(header);

        if (selectedOption.style.display === "none") {
            selectedOption.style.display = "block";
            otherOption0.style.display = "none";
            otherOption1.style.display = "none";
            otherOption3.style.display = "block";
            otherOption4.style.display = "none";
            otherOption5.style.display = "none";
            
        } else {
            selectedOption.style.display = "none";
            otherOption3.style.display = "none";
            otherOption4.style.display = "block";
            otherOption5.style.display = "block";
        }  
 
    }
    else{
        console.log("Element id not specified.");
    }
} 


