package me.liuningning.complier.handle;

import java.util.Map;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import me.liuningning.complier.BindElement;

/**
 * Created by liunn on 2018/4/18.
 */

public interface IAnnotationHandle {
    void handleAnnotation(Map<TypeElement, BindElement> bindElements, RoundEnvironment env);
}
