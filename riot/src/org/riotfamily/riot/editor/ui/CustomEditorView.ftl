<?xml version="1.0" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<@riot.script src="path.js" />
	<script type="text/javascript" language="JavaScript">
		setTimeout(function() {
			updatePath('${editorId}', '${objectId?if_exists}', '${parentId?if_exists}');
		}, 1);
		window.location.replace('${common.url(editorUrl)}');
	</script>
</head>
<body>
</body>
</html>