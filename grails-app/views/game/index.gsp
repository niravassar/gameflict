

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'game.label', default: 'Game')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-game" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-game" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="gameNumber" title="${message(code: 'game.gameNumber.label', default: 'Game Number')}" />
					
						<g:sortableColumn property="date" title="${message(code: 'game.date.label', default: 'Date')}" />
					
						<g:sortableColumn property="time" title="${message(code: 'game.time.label', default: 'Time')}" />

						<g:sortableColumn property="ageGroup" title="${message(code: 'game.time.label', default: 'Age Group')}" />

						<g:sortableColumn property="field" title="${message(code: 'game.time.label', default: 'Field')}" />

						<g:sortableColumn property="homeTeam" title="${message(code: 'game.homeCoach.label', default: 'Home Team')}" />
					
						<g:sortableColumn property="VisitorTeam" title="${message(code: 'game.visitorCoach.label', default: 'Visitor Team')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${gameInstanceList}" status="i" var="gameInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="edit" id="${gameInstance.id}">${gameInstance.gameNumber}</g:link></td>
					
						<td><g:formatDate date="${gameInstance.date}" format="yyyy-MM-dd" /></td>
					
						<td>${fieldValue(bean: gameInstance, field: "time")}</td>

						<td>${fieldValue(bean: gameInstance, field: "ageGroup")}</td>

						<td>${fieldValue(bean: gameInstance, field: "field")}</td>

						<td>${fieldValue(bean: gameInstance, field: "homeTeam")}</td>
					
						<td>${fieldValue(bean: gameInstance, field: "visitorTeam")}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${gameCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
