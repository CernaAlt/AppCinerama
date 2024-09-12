package com.app.cinerma.design.start;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.app.cinerma.R;
import com.app.cinerma.databinding.StartCarruselBinding;
import com.bumptech.glide.Glide;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import java.util.ArrayList;
import java.util.List;

public class MainHomeFragment extends Fragment {

    //Declaramos la variable view Binding
    private StartCarruselBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar(crear) el diseño utilizando View Binding
        binding = StartCarruselBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar el carrusel con los elementos
        initializeCarousel();
    }


    private void initializeCarousel() {
        // Crear la lista de elementos del carrusel
        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem("https://www.contigoalcine.com/assets/peliculas/DEEPWEB-SHOW-MORTAL-F6.jpg", "Pelicula 1"));
        list.add(new CarouselItem("https://upload.wikimedia.org/wikipedia/en/b/ba/Beetlejuice_Beetlejuice_poster.jpg", "Pelicula 2"));
        list.add(new CarouselItem("https://www.bfdistribution.cl/wp-content/uploads/2024/06/POSTER-WEB-1.jpg", "Pelicula 3"));
        list.add(new CarouselItem("https://www.americatv.com.pe/cinescape/wp-content/uploads/2024/08/666b10d032ae3c6e8c614cf40-210x300.jpg", "Pelicula 4"));

        // Establecer los elementos al carrusel
        binding.carrucel.setData(list);
        binding.carrucel.setAutoPlay(true); // Habilitar auto-deslizamiento
        binding.carrucel.setAutoPlayDelay(3000); // Retraso de 3 segundos entre desplazamientos
        binding.carrucel.setShowIndicator(true); // Mostrar los indicadores de página

        // Configurar Glide como cargador de imágenes para el carrusel
        // Si es necesario usar Glide, se puede implementar un cargador de imágenes aquí.


        // Configurar el OnClickListener para los elementos del carrusel
        /*binding.carrucel.setOnItemClickListener((carouselItem, position) -> {
            // Aquí puedes manejar el evento de clic en un elemento del carrusel
            // Por ejemplo, redirigir a otra actividad o fragmento
            handleCarouselItemClick(position);
        });*/
    }

    //logica cuando hacemos un clik en los elemntos de cada pelicula
    /*private void handleCarouselItemClick(int position) {
        switch (position) {
            case 0:
                // Navegar a la primera pantalla
                navigateToActivity(FirstActivity.class);
                break;
            case 1:
                // Navegar a la segunda pantalla
                navigateToActivity(SecondActivity.class);
                break;
            case 2:
                // Navegar a la tercera pantalla
                navigateToActivity(ThirdActivity.class);
                break;
            case 3:
                // Navegar a la cuarta pantalla
                navigateToActivity(FourthActivity.class);
                break;
            default:
                // Manejar el caso por defecto
                break;
        }
    }

    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        startActivity(intent);
    }*/



    //codigo Anterior
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        View view = inflater.inflate(R.layout.start_carrusel, container, false);

        // Obtener la referencia al carrusel
        ImageCarousel carousel = view.findViewById(R.id.carrucel);

        // Configurar Glide como cargador de imágenes para el carrusel
        //carousel.setImageLoader((imageView, url) -> {
         //   Glide.with(this).load(url).into(imageView);
        //});

        // Crear la lista de elementos del carrusel
        List<CarouselItem> list = new ArrayList<>();

        list.add(new CarouselItem("https://www.contigoalcine.com/assets/peliculas/DEEPWEB-SHOW-MORTAL-F6.jpg", "Pelicula 1"));
        list.add(new CarouselItem("https://upload.wikimedia.org/wikipedia/en/b/ba/Beetlejuice_Beetlejuice_poster.jpg", "Pelicula 2"));
        list.add(new CarouselItem("https://www.bfdistribution.cl/wp-content/uploads/2024/06/POSTER-WEB-1.jpg", "Pelicula 3"));
        list.add(new CarouselItem("https://www.americatv.com.pe/cinescape/wp-content/uploads/2024/08/666b10d032ae3c6e8c614cf40-210x300.jpg","Pelicula 4"));

        // Establecer los elementos al carrusel
        carousel.setData(list);
        carousel.setAutoPlay(true); // Habilitar auto-deslizamiento
        carousel.setAutoPlayDelay(3000); // Retraso de 3 segundos entre desplazamientos
        carousel.setShowIndicator(true); // Mostrar los indicadores de página


        return view;
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Limpiar la referencia de binding para evitar fugas de memoria
        binding = null;
    }
}
