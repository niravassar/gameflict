



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

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'homeCoach', 'error')} ">
	<label for="homeCoach">
		<g:message code="game.homeCoach.label" default="Home Coach" />
		
	</label>
	<g:textField name="homeCoach" value="${gameInstance?.homeCoach}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'visitorCoach', 'error')} ">
	<label for="visitorCoach">
		<g:message code="game.visitorCoach.label" default="Visitor Coach" />
		
	</label>
	<g:textField name="visitorCoach" value="${gameInstance?.visitorCoach}"/>

</div>

