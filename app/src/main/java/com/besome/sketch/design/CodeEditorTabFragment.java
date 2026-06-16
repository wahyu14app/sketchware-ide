package com.besome.sketch.design;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.github.rosemoe.sora.widget.CodeEditor;
import pro.sketchware.utility.EditorUtils;
import a.a.a.yq;
import mod.hey.studios.util.Helper;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.R;
import java.io.File;
import com.besome.sketch.beans.ProjectFileBean;

public class CodeEditorTabFragment extends Fragment {
    private CodeEditor editor;
    private ProjectFileBean currentProjectFile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        editor = new CodeEditor(requireContext());
        editor.setTypefaceText(EditorUtils.getTypeface(requireContext()));
        editor.setTextSize(14);
        EditorUtils.loadJavaConfig(editor);
        return editor;
    }

    public void setCurrentActivity(ProjectFileBean projectFile) {
        this.currentProjectFile = projectFile;
    }

    public void refreshEvents() {
        if (currentProjectFile == null) return;
        String javaName = currentProjectFile.getJavaName();
        String sc_id = DesignActivity.sc_id;
        
        String javaDir = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/java/";
        File ungeneratedFile = new File(javaDir + javaName);
        if (ungeneratedFile.exists()) {
            editor.setText(FileUtil.readFile(ungeneratedFile.getAbsolutePath()));
        } else {
            yq generator = new yq(requireContext(), sc_id);
            String generatedCode = generator.getFileSrc(javaName, a.a.a.jC.b(sc_id), a.a.a.jC.a(sc_id), a.a.a.jC.c(sc_id));
            editor.setText(generatedCode);
        }
    }
    
    public void save() {
        if (currentProjectFile == null) return;
        String javaName = currentProjectFile.getJavaName();
        String sc_id = DesignActivity.sc_id;
        String javaDir = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/files/java/";
        FileUtil.makeDir(javaDir);
        FileUtil.writeFile(javaDir + javaName, editor.getText().toString());
    }

    public void c() {}
    public void toggleSearchBar() {}
    
    @Override
    public void onPause() {
        super.onPause();
        save();
    }
}
