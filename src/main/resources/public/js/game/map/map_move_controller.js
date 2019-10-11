(function MapMoveController(){
    const MIN_SCALE = 0.5;
    const MAX_SCALE = 1;

    let scale = 1;
    const scaleStep = 0.2

    window.mapMoveController = new function(){
        this.addListeners = function(){
            let lastX = 0;
            let lastY = 0;
            let clicked = false;
            let clickedButton;
            const map = document.getElementById("map-elements-container");
                //Egérkattintások mappelése
                map.onmousedown = function(event){
                    lastX = event.clientX;
                    lastY = event.clientY;
                    clicked = true;
                    clickedButton = event.button;
                };
                map.onmouseup = function(){clicked = false;};

                //Térkép mozgatása
                map.onmousemove = function(event){
                    if(clicked && clickedButton == 2){
                        const diffX = lastX - event.clientX;
                        const diffY = lastY - event.clientY;
                        document.getElementById("map-container").scrollBy(diffX * 1.5, diffY * 2.5);
                        lastX = event.clientX;
                        lastY = event.clientY;
                    }
                };

                //Görgővel való görgetés tiltása
                map.onwheel = function(event){
                    event.preventDefault();
                    return
                    //TODO fix scaling
                    const delta = event.deltaY;
                    const newScale =  scale - delta * scaleStep / 100;
                    if(newScale < MIN_SCALE || newScale > MAX_SCALE){
                        return;
                    }

                    scale = newScale;
                    map.style.transform = "scale(" + scale + ")";
                };
        }
    };
})();