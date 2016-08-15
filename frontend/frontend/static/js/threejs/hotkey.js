// listening
document.addEventListener( 'keydown', onDocumentKeyDown, false );


// HOTKEYS:
function onDocumentKeyDown(event){

	// what key was pressed
	var keycode = event.which;


	// z = zoom extents to visible objects
	if (keycode == 90){

		var x = []; var y = [];	var z = [];

		for (i = 0 ; i < (objects.length); i++) {
			obj = objects[i];
			if (obj.visible == true){
				x.push(obj.geometry.vertices[0].x);
				y.push(obj.geometry.vertices[0].y);
				z.push(obj.geometry.vertices[0].z);
			}
		}
		
		x.sort(sortNumber);
		y.sort(sortNumber);
		z.sort(sortNumber);

		// calculate model radii
		xr=(x[x.length-1]-x[0])*0.5;
		yr=(y[y.length-1]-y[0])*0.5;
		zr=(z[z.length-1]-z[0])*0.5;
		r = Math.max(xr,yr,zr);

		// calculate center of objects
		xm=x[0]+xr;
		ym=y[0]+yr;
		zm=z[0]+zr;

		// define unit vector from objects to current camera position
		currentNormal = new THREE.Vector3(camera.position.x-xm,camera.position.y-ym,camera.position.z-zm).normalize();

		// scale camera position accordingly to fit currently visible objects
		camera.position.x = xm + currentNormal.x*r*16;
		camera.position.y = ym + currentNormal.y*r*16;
		camera.position.z = zm + currentNormal.z*r*16;
		controls.target = new THREE.Vector3(xm,ym,zm);
	}


	// h = hide selected objects
	else if (keycode == 72){
		for (i = 0 ; i < (objects.length); i++){
			obj = objects[i];
			if (obj.selected === true) {
				obj.visible = false;
				obj.selected = false;
				obj.material.color.setHex(obj.prevColor);
			}
		}
	}


	// i = isolate selected objects
	else if (keycode == 73){
		for (i = 0 ; i < (objects.length); i++){
			obj = objects[i];
			if (obj.selected === true) {
				obj.selected = false;
				obj.material.color.setHex(obj.prevColor);
			}
			else {
				obj.visible = false;
				obj.selected = false;
			}
		}
	}


	// s = show all objects
	else if (keycode == 83){
		for (i = 0 ; i < (objects.length); i++){
			obj = objects[i];
			obj.visible = true;
		}
	}
}