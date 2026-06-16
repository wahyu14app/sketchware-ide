package com.besome.sketch.design;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.besome.sketch.beans.ProjectFileBean;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class AIChatTabFragment extends Fragment {
    private ProjectFileBean currentProjectFile;
    private LinearLayout chatContainer;
    private ScrollView scrollView;
    private EditText inputField;
    private ImageView sendButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout root = new LinearLayout(requireContext());
        root.setOrientation(LinearLayout.VERTICAL);

        scrollView = new ScrollView(requireContext());
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        scrollView.setLayoutParams(scrollParams);
        
        chatContainer = new LinearLayout(requireContext());
        chatContainer.setOrientation(LinearLayout.VERTICAL);
        chatContainer.setPadding(16, 16, 16, 16);
        scrollView.addView(chatContainer);
        root.addView(scrollView);

        LinearLayout inputLayout = new LinearLayout(requireContext());
        inputLayout.setOrientation(LinearLayout.HORIZONTAL);
        inputLayout.setPadding(16, 16, 16, 16);

        inputField = new EditText(requireContext());
        inputField.setHint("Ask AI to write code or fix bugs...");
        inputField.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        inputLayout.addView(inputField);

        sendButton = new ImageView(requireContext());
        sendButton.setImageResource(android.R.drawable.ic_menu_send);
        sendButton.setPadding(16, 16, 16, 16);
        inputLayout.addView(sendButton);

        root.addView(inputLayout);

        sendButton.setOnClickListener(v -> {
            String text = inputField.getText().toString();
            if(!text.trim().isEmpty()) {
                addMessage("You: " + text);
                inputField.setText("");
                
                // Mock response
                addMessage("AI: I am ready to help you write code for " + (currentProjectFile != null ? currentProjectFile.getJavaName() : "your project") + ". Please configure my API key in Settings.");
            }
        });

        return root;
    }

    private void addMessage(String message) {
        TextView tv = new TextView(requireContext());
        tv.setText(message);
        tv.setPadding(0, 8, 0, 8);
        tv.setTextSize(14);
        chatContainer.addView(tv);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    public void setProjectFile(ProjectFileBean projectFile) {
        this.currentProjectFile = projectFile;
    }

    public void refreshData() {
        // Refresh API info or current context if needed
    }

    public void unselectAll() {}
}
