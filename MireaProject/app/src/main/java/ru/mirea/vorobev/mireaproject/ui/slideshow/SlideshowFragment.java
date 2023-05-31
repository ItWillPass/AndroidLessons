package ru.mirea.vorobev.mireaproject.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.vorobev.mireaproject.R;
import ru.mirea.vorobev.mireaproject.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
    private int currentIndex = 0;
    private ImageView imageView;
    private TextView slideView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        imageView = root.findViewById(R.id.imageView);
        imageView.setOnClickListener(this::onClick);
        updateImage();
        slideView= root.findViewById(R.id.text_slideshow);
        slideView.setText("Нажми на картинку!");

        return root;
    }
    public void onClick(View v) {
        currentIndex = (currentIndex + 1) % images.length;
        updateImage();
    }

    private void updateImage() {
        imageView.setImageResource(images[currentIndex]);
    }
}