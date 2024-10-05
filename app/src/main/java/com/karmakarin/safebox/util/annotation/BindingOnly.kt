package com.karmakarin.safebox.util.annotation


@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)

@DslMarker
@Retention(AnnotationRetention.BINARY)
annotation class BindingOnly()
