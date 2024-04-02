package com.likeminds.likemindschat.community.model

class Question private constructor(
    val id: Int?,
    val questionTitle: String?,
    val state: Int,
    val value: String?,
    val optional: Boolean,
    val helpText: String?,
    val field: Boolean?,
    val isCompulsory: Boolean?,
    val isHidden: Boolean?,
    val communityId: String?,
    val memberId: String?,
    val directoryFields: Boolean?,
    val imageUrl: String?,
    val canAddOtherOptions: Boolean?,
    val questionChangeState: Int?,
    val isAnswerEditable: Boolean
) {
    class Builder {
        private var id: Int? = null
        private var questionTitle: String? = null
        private var state: Int = -1
        private var value: String? = null
        private var optional: Boolean = false
        private var helpText: String? = null
        private var field: Boolean? = null
        private var isCompulsory: Boolean? = null
        private var isHidden: Boolean? = null
        private var communityId: String? = null
        private var memberId: String? = null
        private var directoryFields: Boolean? = null
        private var imageUrl: String? = null
        private var canAddOtherOptions: Boolean? = null
        private var questionChangeState: Int? = -1
        private var isAnswerEditable: Boolean = true

        fun id(id: Int?) = apply { this.id = id }
        fun questionTitle(questionTitle: String?) = apply { this.questionTitle = questionTitle }
        fun state(state: Int) = apply { this.state = state }
        fun value(value: String?) = apply { this.value = value }
        fun optional(optional: Boolean) = apply { this.optional = optional }
        fun helpText(helpText: String?) = apply { this.helpText = helpText }
        fun field(field: Boolean?) = apply { this.field = field }
        fun isCompulsory(isCompulsory: Boolean?) = apply { this.isCompulsory = isCompulsory }
        fun isHidden(isHidden: Boolean?) = apply { this.isHidden = isHidden }
        fun communityId(communityId: String?) = apply { this.communityId = communityId }
        fun memberId(memberId: String?) = apply { this.memberId = memberId }
        fun directoryFields(directoryFields: Boolean?) =
            apply { this.directoryFields = directoryFields }

        fun imageUrl(imageUrl: String?) = apply { this.imageUrl = imageUrl }

        fun canAddOtherOptions(canAddOtherOptions: Boolean?) =
            apply { this.canAddOtherOptions = canAddOtherOptions }

        fun questionChangeState(questionChangeState: Int?) =
            apply { this.questionChangeState = questionChangeState }

        fun isAnswerEditable(isAnswerEditable: Boolean) =
            apply { this.isAnswerEditable = isAnswerEditable }

        fun build() = Question(
            id,
            questionTitle,
            state,
            value,
            optional,
            helpText,
            field,
            isCompulsory,
            isHidden,
            communityId,
            memberId,
            directoryFields,
            imageUrl,
            canAddOtherOptions,
            questionChangeState,
            isAnswerEditable
        )
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .questionTitle(questionTitle)
            .state(state)
            .value(value)
            .optional(optional)
            .helpText(helpText)
            .field(field)
            .isCompulsory(isCompulsory)
            .isHidden(isHidden)
            .communityId(communityId)
            .memberId(memberId)
            .directoryFields(directoryFields)
            .imageUrl(imageUrl)
            .canAddOtherOptions(canAddOtherOptions)
            .isAnswerEditable(isAnswerEditable)
            .questionChangeState(questionChangeState)
    }
}