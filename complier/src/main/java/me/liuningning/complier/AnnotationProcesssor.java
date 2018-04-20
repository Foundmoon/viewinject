package me.liuningning.complier;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import me.liuningning.complier.handle.BindViewHandle;
import me.liuningning.complier.handle.IAnnotationHandle;
import me.liuniningning.annotation.BindView;

/**
 * Created by liunn on 2018/4/18.
 */

@AutoService(Processor.class)
public class AnnotationProcesssor extends AbstractProcessor {


    private Filer mFiler;
    private Messager mLog;

    private List<IAnnotationHandle> mHandles = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mLog = processingEnvironment.getMessager();
        d("init");

        registerHandle();
    }

    private void registerHandle() {

        mHandles.add(new BindViewHandle());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        d("process");
        Map<TypeElement, BindElement> map = new HashMap<>();
        for (IAnnotationHandle handle : mHandles) {
            handle.handleAnnotation(map, roundEnvironment);
        }

        for (Map.Entry<TypeElement, BindElement> entry : map.entrySet()) {

            JavaFile javaFile = entry.getValue().generateJavaFile();
            try {
                if(javaFile!=null) {
                    javaFile.writeTo(mFiler);
                }
            } catch (IOException e) {
                d("javaFile write error");
            }

        }


        return true;
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> types = new LinkedHashSet<>();

        types.add(BindView.class.getCanonicalName());

        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    private void d(String note) {
        mLog.printMessage(Diagnostic.Kind.NOTE, note);
    }

    private void e(String note) {
        mLog.printMessage(Diagnostic.Kind.ERROR, note);
    }

}
