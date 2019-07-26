package com.jgarin.workflowone

import com.jgarin.base.BaseEvent

internal sealed class Event : BaseEvent {
	object GoToNext : Event()
	object GoToInput : Event()
	class InputChanged(val input: String) : Event()
	object SaveInput : Event()
	object CancelInput : Event()
	object OnBackPressed: Event()
}