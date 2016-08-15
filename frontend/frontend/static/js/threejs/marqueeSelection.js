mousedown = false;
document.addEventListener( 'mousedown', mouseDown, false );
document.addEventListener( 'mouseup', mouseUp, false );
document.addEventListener( 'mousemove', marqueeSelect, false );


    function resetMarquee () {
        mouseup = true
        mousedown = false;
        marquee.fadeOut();
        marquee.css({width: 0, height: 0});
        mousedowncoords = {};
    }
 
    function mouseDown (event) {

      // ignore for right mouse. select with left mouse only
      if ( event.button === 2) {
        return;
      }

      if (typeof selectedObjs === 'object'){
        for (var i = 0; i < selectedObjs.length; i++) {
          selectedObjs[i].line.material.color.setHex(prevColor);
        }
      }
 
      event.preventDefault();
 
      var pos = {};
 
      mousedown = true;
      mousedowncoords = {};
 
      mousedowncoords.x = event.clientX;
      mousedowncoords.y = event.clientY;

 
      // adjust the mouse select. get 2D vector.
      var canvasWidth = document.getElementById('canvas-model').offsetWidth;
      var pedigreeWidth = document.getElementById('canvas-pedigree').offsetWidth;
      pos.x = ((event.clientX - offset.x - pedigreeWidth) / canvasWidth) * 2 -1;
      pos.y = -((event.clientY - offset.y) / window.innerHeight) * 2 + 1;
      var vector = new THREE.Vector3(pos.x, pos.y, 1);
 
      vector.unproject(camera);
    }
 
    function mouseUp (event) {
      event.preventDefault();
      event.stopPropagation();
 
      // reset the marquee selection
      resetMarquee();
    }
 
    function marqueeSelect (event) {

      event.preventDefault();
      event.stopPropagation();
 
      // make sure we are in a select mode.
      if(mousedown){
 
        marquee.fadeIn();
 
        var pos = {};
        // =(mouseup position - mousedown position) to define dims of rectangle
        pos.x = event.clientX - mousedowncoords.x;
        pos.y = event.clientY - mousedowncoords.y;

 
        // square variations
        // (0,0) origin is the TOP LEFT pixel of the canvas.
        //
        //  1 | 2
        // ---o---
        //  4 | 3
        // there are 4 ways a square can be gestured onto the screen.  the following detects these four variations
        // and creates/updates the CSS to draw the square on the screen
        if (pos.x < 0 && pos.y < 0) {
            marquee.css({left: event.clientX + 'px', width: -pos.x + 'px', top: event.clientY + 'px', height: -pos.y + 'px'});
        } else if ( pos.x >= 0 && pos.y <= 0) {
            marquee.css({left: mousedowncoords.x + 'px',width: pos.x + 'px', top: event.clientY, height: -pos.y + 'px'});
        } else if (pos.x >= 0 && pos.y >= 0) {
            marquee.css({left: mousedowncoords.x + 'px', width: pos.x + 'px', height: pos.y + 'px', top: mousedowncoords.y + 'px'});
        } else if (pos.x < 0 && pos.y >= 0) {
            marquee.css({left: event.clientX + 'px', width: -pos.x + 'px', height: pos.y + 'px', top: mousedowncoords.y + 'px'});
        }

        var selectedObjs = findObjsByVertices({x: event.clientX, y: event.clientY});
 
          for (var i = 0; i < selectedObjs.length; i++) {

            var obj = selectedObjs[i].line;
            obj.material.color.setHex( 0x000000 );
            obj.selected = true;
          }
        }
    }
 
    function findObjsByVertices(location){
      var currentMouse = {},
          mouseInitialDown = {},
          units,
          bounds,
          inside = false,
          selectedUnits = [],
          dupeCheck = {};
          var canvasWidth = document.getElementById('canvas-model').offsetWidth
          var pedigreeWidth = document.getElementById('canvas-pedigree').offsetWidth;
 
      currentMouse.x = location.x - pedigreeWidth;
      currentMouse.y = location.y;

      mouseInitialDown.x = (mousedowncoords.x - pedigreeWidth - offset.x);
      mouseInitialDown.y = (mousedowncoords.y - offset.y);
 
      units = getUnitVertCoordinates();
      bounds = findBounds(currentMouse, mousedowncoords);
 
      for(var i = 0; i < units.length; i++) {
        inside = withinBounds(units[i].pos2D, bounds);
        if(inside && (dupeCheck[units[i].id] === undefined)){
          selectedUnits.push(units[i]);
          dupeCheck[units[i].name] = true;
        }
      }
 
      return selectedUnits;
    }
 
    // takes the mouse up and mouse down positions and calculates an origin
    // and delta for the square.
    // this is compared to the unprojected XY centroids of the cubes.
    function findBounds (pos1, pos2) {
 
        // calculating the origin and vector.
        var origin = {},
            delta = {};
 
        if (pos1.y < pos2.y) {
            origin.y = pos1.y;
            delta.y = pos2.y - pos1.y;
        } else {
            origin.y = pos2.y;
            delta.y = pos1.y - pos2.y;
        }
 
        if(pos1.x < pos2.x) {
            origin.x = pos1.x;
            delta.x = pos2.x - pos1.x;
        } else {
            origin.x = pos2.x;
            delta.x = pos1.x - pos2.x;
        }
        return ({origin: origin, delta: delta});
    }
 
 
    // Takes a position and detect if it is within delta of the origin defined by findBounds ({origin, delta})
    function withinBounds(pos, bounds) {
 
        var ox = bounds.origin.x,
            dx = bounds.origin.x + bounds.delta.x,
            oy = bounds.origin.y,
            dy = bounds.origin.y + bounds.delta.y;
 
        if((pos.x >= ox) && (pos.x <= dx)) {
            if((pos.y >= oy) && (pos.y <= dy)) {
                return true;
            }
        }
 
        return false;
    }
 
    function getUnitVertCoordinates (threeJsContext) {
 
      var units = [],
          verts = [],
          child,
          prevChild,
          unit,
          vector,
          pos,
          temp,
          i, q;
 
      for(i = 0; i < objects.length; i++) {
          child = objects[i];
          child.updateMatrixWorld();
 
          verts = [
              child.geometry.vertices[0],
              child.geometry.vertices[1],
              // child.geometry.vertices[2],
              // child.geometry.vertices[3],
              // child.geometry.vertices[4],
              // child.geometry.vertices[5],
              // child.geometry.vertices[6],
              // child.geometry.vertices[7]
          ];

 
          for(q = 0; q < verts.length; q++) {
              vector = verts[q].clone();
              vector.applyMatrix4(child.matrixWorld);
              unit = {};
              unit.id = child.id;
              unit.line = child;
              unit.pos2D = toScreenXY(vector);
              units.push(unit);

          }
      }
      return units;
    }
 
    function toScreenXY (position) {
      var canvasWidth = document.getElementById('canvas-model').offsetWidth
      var pedigreeWidth = document.getElementById('canvas-pedigree').offsetWidth;
 
      var pos = position.clone();
      var projScreenMat = new THREE.Matrix4();
      projScreenMat.multiplyMatrices( camera.projectionMatrix, camera.matrixWorldInverse );
      pos.applyProjection(projScreenMat);
 
      return { x: ( pos.x + 1 ) * canvasWidth / 2 + offset.x,
           y: ( - pos.y + 1) * window.innerHeight / 2 + offset.y };
 
      // return { x: ( pos.x + 1 ) * window.innerWidth / 2 + offset.x,
      //      y: ( - pos.y + 1) * window.innerHeight / 2 + offset.y };
    }