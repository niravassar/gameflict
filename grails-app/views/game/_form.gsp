<%@ page import="org.gssa.gameflict.Field; org.gssa.gameflict.Team; org.gssa.gameflict.AgeGroup" %>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'gameNumber', 'error')} required">
	<label for="gameNumber">
		<g:message code="game.gameNumber.label" default="Game Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="gameNumber" type="number" value="${gameInstance.gameNumber}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="game.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${gameInstance?.date}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'time', 'error')} required">
	<label for="time">
		<g:message code="game.time.label" default="Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="time" required="" value="${gameInstance?.time}"/>
	<b>ex format: 9:00 AM</b>
</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'ageGroup', 'error')} required">
	<label for="ageGroup">
		<g:message code="game.ageGroup.label" default="Age Group" />
		<span class="required-indicator">*</span>
	</label>
	${gameInstance?.ageGroup}

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'field', 'error')} required">
	<label for="field">
		<g:message code="game.ageGroup.label" default="Field" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="field" from="${Field.list()}" value="${gameInstance?.field?.id}" optionKey="id"/>

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'homeTeam', 'error')} ">
	<label for="homeCoach">
		<g:message code="game.homeCoach.label" default="Home Team" />
		
	</label>
	${gameInstance?.homeTeam}

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'visitorTeam', 'error')} ">
	<label for="visitorCoach">
		<g:message code="game.visitorCoach.label" default="Visitor Team" />
		
	</label>
	${gameInstance?.visitorTeam}

</div>

