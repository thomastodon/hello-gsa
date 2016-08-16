// selections line geometry. changes color of selected object to black. displays associated analysisResibute values of object in HUD at top left of viewport


// listening
document.addEventListener( 'mousedown', onDocumentMouseDown, false );

// object selection
var prev = []
function onDocumentMouseDown( event ) {

	if ( event.button === 2) {
	return;
	}

	var vis = [];
	for (i = 0 ; i < (objects.length); i++) {
		obj = objects[i];
		if (obj.visible == true){
			vis.push(obj);
		}
	}

	var offset = {};
      offset.x = 6;
      offset.y = 6;
    var canvasWidth = document.getElementById('canvas-model').offsetWidth
	// var canvasHeight = document.getElementById('canvas-model').offsetHeight

	// take normalized mouse vector in 2D and translate to 3D
	// mouse.x = ((event.clientX - offset.x) / window.innerWidth ) * 2 - 1;

	var canvasWidth = document.getElementById('canvas-model').offsetWidth;
	var pedigreeWidth = document.getElementById('canvas-pedigree').offsetWidth;
	mouse.x = ((event.clientX - offset.x - pedigreeWidth) / canvasWidth) * 2 -1;
	mouse.y = - ((event.clientY - offset.y) / window.innerHeight) * 2 + 1;
	var mouse3D = new THREE.Vector3( mouse.x, mouse.y, 0.5 );
	mouse3D.unproject( camera ); // this is where the mouse is (in front of the camera)
	
	origin = camera.position; // this is where the camera is
	
	mouse3D.sub(origin); // this is a ray from the camera to the mouse
	mouse3D.normalize(); // now its a unit vector

	var raycaster = new THREE.Raycaster( origin, mouse3D );
	raycaster.linePrecision = 400;

	var intersects = raycaster.intersectObjects( vis );

	// info legend is displayed in top left
	if ( intersects.length > 0 ) {

		// current object
		var obj = intersects[ 0 ].object;

		console.log(obj);

		// TODO: hit endpoint for single forceMoment here
		// TODO: don't include forceMoments on Element Json response
		
		// if previous selection exists, revert to original color
		prevObj = prev[0];
		prevClr = prev[1];
		if ( typeof prevObj !== 'undefined'){
			prevObj.material.color.setHex( prevClr );
		}

		analysisRes = obj['analysisRes']
		analysisResCount = Object.keys(analysisRes).length;

		// render analysis result values to the canvas
		document.getElementById("info").innerHTML = "";
		for (var key in analysisRes) {
			if (analysisRes.hasOwnProperty(key)) {
				document.getElementById("info").innerHTML += key + ': ' + analysisRes[key];
				document.getElementById("info").innerHTML += '<br>';
			}
		}

		// store current selection color to be reverted to on next iteration
		prev[0]=obj;
		prev[1]=obj.material.color.getHex();

		// current object colored black
		obj.material.color.setHex( 0x000000 );	
	}
	else { // click away into white space to clear selection
		prevObj = prev[0];
		prevClr = prev[1];
		prevObj.material.color.setHex( prevClr );
	}
	
}