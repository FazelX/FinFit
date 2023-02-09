package dev.fazelx.finfit.models.remote.requests

import com.google.gson.annotations.SerializedName

data class CreateExpenseRequestModel(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)