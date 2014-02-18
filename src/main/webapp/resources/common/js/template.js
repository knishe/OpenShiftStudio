/**
 * Extends from nano https://github.com/trix/nano
 */
csm.nano = function(template, data) {
	return template.replace(/\{([\w\.]*)\}/g, function(str, key) {
		var keys = key.split("."), v = data[keys.shift()];
		for (var i = 0, l = keys.length; i < l; i++) {
			v = v[keys[i]];
		}
		return (typeof v !== "undefined" && v !== null) ? v : "";
	});
}

csm.template = function(template, data) {
	var str = template;
	if (template instanceof jQuery) {
		str = template.html();
	}
	return csm.nano(str, data);
}
