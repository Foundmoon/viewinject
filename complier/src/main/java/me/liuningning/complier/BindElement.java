package me.liuningning.complier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import me.liuningning.complier.entry.BindField;

/**
 * Created by liunn on 2018/4/18.
 */

public class BindElement {


    private TypeElement mTypeElement;
    private static final ClassName UNBINDER = ClassName.get("me.liuningning.inject", "UnBinder");
    private static final ClassName UNTILS = ClassName.get("me.liuningning.inject", "Utils");

    private static final ClassName VIEW = ClassName.get("android.view", "View");

    private List<BindField> mBindFields = new ArrayList<>();

    public static final String SUFFIX = "ViewBind";


    public void setTypeElement(TypeElement typeElement) {
        mTypeElement = typeElement;
    }

    public List<BindField> getBindFields() {
        return mBindFields;
    }


    @Override
    public String toString() {
        return "BindElement{" + "mBindFields=" + mBindFields + '}';
    }


    public JavaFile generateJavaFile() {


        String className = mTypeElement.getSimpleName().toString() + SUFFIX;
        PackageElement packageElement = (PackageElement) mTypeElement.getEnclosingElement();
        String packageName = packageElement.getQualifiedName().toString();
        TypeName targetTypeName = TypeName.get(mTypeElement.asType());



        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(className);

        FieldSpec targetField = FieldSpec.builder(targetTypeName, "target", Modifier.PRIVATE).build();
        FieldSpec sourceField = FieldSpec.builder(VIEW, "source", Modifier.PRIVATE).build();

        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .addParameter(VIEW, "source")
                .addStatement("this.target = target")
                .addStatement("this.source = source")
                .build();


        MethodSpec.Builder bindMethod = MethodSpec.methodBuilder("bind")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID);
        for (BindField bindField : mBindFields) {
            TypeMirror typeMirror = bindField.getTypeMirror();
            TypeName typeName = TypeName.get(typeMirror);
            String fieldName = bindField.getFieldName();
            int resId = bindField.getResId();
            bindMethod.addStatement("target.$N = $T.findView(source,$L);", fieldName, UNTILS, resId);
        }


        typeSpecBuilder
                .addSuperinterface(UNBINDER)//super interface
                .addMethod(constructor)//constructor
                .addField(targetField)//target field
                .addField(sourceField)//source field
                .addMethod(bindMethod.build());

        JavaFile file = JavaFile.builder(packageName, typeSpecBuilder.build()).build();

        return file;

    }


}
