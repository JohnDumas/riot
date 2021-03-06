<@template.set stylesheets=[
	"style/form.css", 
	"style/form-custom.css",
	"style/command.css", 
	"style/command-custom.css"
] />
<@template.extend file="../screen.ftl">
			
	<@template.block name="main">
	    <script>
	       /**
	        * Strips the hash sign to convert the URL of newly created objects into their canonical form. 
	        */
	       function getFormUrl() {
	           return window.location.href.replace(/^(.*?)(?:\/-\/.+\/.+|\/)#(.+)$/, '$1/$2');
	       }
	       
	       /**
	        * Invoked by FormScreen upon save.
	        */
	       function setObjectId(id) {
	           window.location.hash = id;
	           riot.form.setUrl(getFormUrl());
	       }
	       
	       /**
	        */
	       if (window.location.hash.length > 0) {
               window.location.replace(getFormUrl());
           }
	    </script>
		<@riot.script src="/engine.js" />
		<@riot.script src="/util.js" />
		<@riot.script src="/interface/ListService.js" />
		<@riot.script src="list.js" />
		<div id="form">${form}</div>
	</@template.block>
		
	<@template.block name="extra">
		<div class="box">
			<div class="commands">
				<!--<a class="action enabled" href="javascript:save()"><span class="icon-and-label"><span class="icon saveButton"></span><span class="label"><@c.message "label.form.button.save">Save</@c.message></span></span></a>-->
				<div id="commands"></div>
			</div>
		</div>
	
		<script type="text/javascript" language="JavaScript">
			<#if context.objectId??>
				var list = new RiotList('${listStateKey}');
				list.renderFormCommands({objectId: '${context.objectId}'}, 'commands');
			</#if>
		</script>
		
		<#if childLinks??>
			<div class="box">
				<div class="links">
					<#list childLinks as child>
						<a class="screen" href="${c.url(child.url)}" style="${riot.iconStyle(child.icon!"brick")}">
							${child.title}
						</a>
					</#list>
				</div>
			</div>
		</#if>
		
	</@template.block>

</@template.extend>