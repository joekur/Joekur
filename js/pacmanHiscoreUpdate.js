function checkRecentActivity() {
    newItem = $("<li>Item " + itemCount + "</li>").hide();
    
    $("#rec_activity").prepend(newItem);
    newItem.slideDown('slow');
    

    itemCount++;
    setTimeout(checkRecentActivity, 5000);
}

setTimeout(checkRecentActivity, 5000);