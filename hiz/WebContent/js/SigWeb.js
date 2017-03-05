/**
 * 
 */

var tmr;
var txtSign;

function onSign() {
	txtSign = document.getElementById('form:txtSign');
	txtSign.value = null;
	var ctx = document.getElementById('cnv').getContext('2d');
	SetDisplayXSize(500);
	SetDisplayYSize(100);
	SetJustifyMode(0);
	ClearTablet();
	tmr = SetTabletState(1, ctx, 50) || tmr;
}

function onClear() {
	ClearTablet();
}

function onDone() {
	var count = NumberOfTabletPoints();
	if (count == 0 || !txtSign) {
		showSignEmpty();
	} else {
		SetTabletState(0, tmr);
		SetSigCompressionMode(1);
		SetImageXSize(500);
		SetImageYSize(100);
		SetImagePenWidth(5);
		GetSigImageB64(SigImageCallback);
	}
}

function SigImageCallback(str) {
	txtSign.value = "data:image/png;base64," + str;
	saveConsentTextAction();
}

function closingSigWeb() {
	ClearTablet();
	SetTabletState(0, tmr);
}