var preloadIMG = function(imageArray){
	var loadImages = [];
	
	for(var i = 0; i<imageArray.length; i++){
		loadImages[i] = new Image();
		loadImages[i].src = "images/"+imageArray[i];
	};
}