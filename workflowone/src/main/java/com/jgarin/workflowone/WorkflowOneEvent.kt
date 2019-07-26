package com.jgarin.workflowone

import com.jgarin.base.BaseEvent

internal sealed class WorkflowOneEvent : BaseEvent {
	object GoToNext : WorkflowOneEvent()
	object GoToInput : WorkflowOneEvent()
	class InputChanged(val input: String) : WorkflowOneEvent()
	object SaveInput : WorkflowOneEvent()
	object CancelInput : WorkflowOneEvent()
	object OnBackPressed: WorkflowOneEvent()
}