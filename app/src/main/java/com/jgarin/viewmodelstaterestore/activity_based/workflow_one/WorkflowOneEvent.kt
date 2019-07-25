package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import com.jgarin.viewmodelstaterestore.activity_based.base.BaseEvent

sealed class WorkflowOneEvent : BaseEvent {
	object GoToNext : WorkflowOneEvent()
	object GoToInput : WorkflowOneEvent()
	class InputChanged(val input: String) : WorkflowOneEvent()
	object SaveInput : WorkflowOneEvent()
	object CancelInput : WorkflowOneEvent()
}