package me.liuningning.complier.handle;

import java.util.Map;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import me.liuningning.complier.BindElement;
import me.liuningning.complier.entry.BindField;
import me.liuniningning.annotation.BindView;

/**
 * Created by liunn on 2018/4/18.
 * fetch BindView annotation and process
 */

public class BindViewHandle implements IAnnotationHandle {
    @Override
    public void handleAnnotation(Map<TypeElement, BindElement> bindElements, RoundEnvironment env) {

        for (Element element : env.getElementsAnnotatedWith(BindView.class)) {

            TypeElement typeElement = (TypeElement) element.getEnclosingElement();

            BindElement bindElement = bindElements.get(typeElement);

            if (bindElement == null) {
                bindElement = new BindElement();
                bindElement.setTypeElement(typeElement);
                bindElements.put(typeElement, bindElement);
            }


            String name = element.getSimpleName().toString();
            TypeMirror typeMirror = element.asType();
            int resId = element.getAnnotation(BindView.class).value();
            BindField bindField = new BindField(name, typeMirror, resId);

            bindElement.getBindFields().add(bindField);


        }


    }
}
