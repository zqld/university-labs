package audioplaynew;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;
import javafx.util.Duration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class AudioPlayerInputFile extends Application {
    private MediaPlayer mediaPlayer;

    private File selectedFile1;

    private Slider slider; // Ползунок для отображения прогресса воспроизведения
    private Slider timeSlider;
    private List<File> filesInFolder = new ArrayList<>(); // Список файлов в папке
    private int currentIndexAudio = 0; // Индекс текущего файла
    private Label indexLabel; // Метка для отображения индекса текущего файла

    private int fileCounter = 1; // Счетчик для генерации имен записываемых файлов

    private boolean isRecording = false; // Флаг для отслеживания состояния записи

    private boolean isPlaying = false;

    // Поля для записи аудио
    private TargetDataLine targetDataLine;
    private AudioFormat audioFormat;
    private File audioFile;
    private boolean isSliderMovementAllowed = false;
    private boolean isClose = false;

    private final String folderPath = "input_audio";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Проигрыватель аудио");

        timeSlider = new Slider();
        timeSlider.setMin(0);
        timeSlider.setMax(100);
        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (mediaPlayer != null) {
                    if (timeSlider.isValueChanging()) {
                        double duration = mediaPlayer.getTotalDuration().toMillis();
                        mediaPlayer.seek(Duration.millis(duration * timeSlider.getValue() / 100));
                    }
                }
            }
        });

        // Создаем слайдер для управления громкостью звука
        Slider volumeSlider = new Slider(0, 1, 1);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);

        // Обработчик изменения значения слайдера громкости
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                // Устанавливаем новый уровень громкости
                mediaPlayer.setVolume(newValue.doubleValue());
            }
        });

        Button previousButton = new Button("Предыдущий");
        Button nextButton = new Button("Следующий");
        Button recordButton = new Button("Запись");
        Button deleteFilesButton = new Button("Удалить файлы");
        Button playPauseButton = new Button("●");
        Button openButton = new Button("Открыть");

        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(e -> {
            volumeSlider.setValue(volumeSlider.getMax());
            deleteAudio();
            isPlaying = false;
            playPauseButton.setText("●");
        });

        openButton.setOnAction(e -> {
            timeSlider.setValue(0);
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                playPauseButton.setText("●");
            }
            currentIndexAudio = filesInFolder.size()-1;
            openFile(primaryStage);
        });

        indexLabel = new Label("Индекс: ");
        deleteFilesButton.setOnAction(e -> {
            Runtime.getRuntime().gc();
            stopAndDisposeMediaPlayer();
            int del =0;
            while (del<6){
                Runtime.getRuntime().gc();
                deleteFiles();
                del++;
            }
            currentIndexAudio=0;
            updateIndexLabel();
            playPauseButton.setText("●");
            isClose = false;
            isPlaying = false;
        });

        recordButton.setOnAction(e -> {
            timeSlider.setValue(0);
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                playPauseButton.setText("●");
            }
            currentIndexAudio = filesInFolder.size()-1;
            if (!isRecording) {
                startRecording();
                recordButton.setText("Стоп");
            } else {
                stopRecording();
                recordButton.setText("Запись");
            }
        });

        playPauseButton.setOnAction(event -> {
            if (!filesInFolder.isEmpty()){
                if (!isPlaying) {
                    if(currentIndexAudio>=0){
                        playAudio();
                    }
                    playPauseButton.setText("||");
                    isPlaying = true;
                    isSliderMovementAllowed = true;
                } else {
                    if (mediaPlayer != null) {
                        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {

                            if (mediaPlayer.getCurrentTime().equals(mediaPlayer.getTotalDuration())) {
                                isPlaying = false;
                                playPauseButton.setText("●");
                                // Дополнительные действия могут быть добавлены здесь
                            }else {
                                mediaPlayer.pause();
                                playPauseButton.setText("●");
                            }
                        } else {
                            mediaPlayer.play();
                            playPauseButton.setText("||");
                        }
                    }

                }
            }

        });

        previousButton.setOnAction(e -> {

            volumeSlider.setValue(volumeSlider.getMax());
            timeSlider.setValue(0);
            if (mediaPlayer == null) {
                playPrevious();
            } else {
                mediaPlayer.stop();
                mediaPlayer.dispose(); // Освобождаем ресурсы MediaPlayer
                Runtime.getRuntime().gc();
                playPauseButton.setText("●");
                mediaPlayer.pause();
                playPrevious();
            }
        });

        nextButton.setOnAction(e -> {
            volumeSlider.setValue(volumeSlider.getMax());
            timeSlider.setValue(0);
            if (mediaPlayer == null) {
                playNext();
            } else {
                if (filesInFolder.size()-1!=0&&fileCounter<filesInFolder.size()){
                    mediaPlayer.stop();
                    mediaPlayer.dispose(); // Освобождаем ресурсы MediaPlayer
                    Runtime.getRuntime().gc();
                    isPlaying = false;
                }

                playPauseButton.setText("●");
                mediaPlayer.pause();
                playNext();
                isPlaying = false;
            }
        });

        // Создание графического интерфейса
        HBox navigationBox = new HBox(previousButton, nextButton, playPauseButton,deleteFilesButton, deleteButton, recordButton, openButton, volumeSlider);
        navigationBox.setAlignment(Pos.CENTER);
        VBox root = new VBox(timeSlider, navigationBox, indexLabel);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 800, 250);
        primaryStage.setScene(scene);

        primaryStage.show();
        updateIndexLabel();

        scene.setOnDragOver(event -> {
            timeSlider.setValue(0);
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                playPauseButton.setText("●");
            }
            currentIndexAudio = filesInFolder.size()-1;
            isClose = false;
            if (event.getGestureSource() != scene && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        scene.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                for (File file : db.getFiles()) {
                    if (file.isFile() && isSupportedFileType(file)) {
                        // Поддерживаемый тип файла, копируем его в папку и добавляем в список файлов
                        copyFileToFolder(file);
                        success = true;
                    } else {
                        // Неподдерживаемый тип файла, выведите сообщение об ошибке или выполните другие действия
                        showAlert("Ошибка", "Неподдерживаемый формат файла: " + file.getName());
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
            playNext();
        });


        primaryStage.setOnCloseRequest(event -> {
            Runtime.getRuntime().gc();
            stopAndDisposeMediaPlayer();
            int del =0;
            while (del<6){
                Runtime.getRuntime().gc();
                deleteFiles();
                del++;
            }
            if (mediaPlayer != null) {
                Runtime.getRuntime().gc();
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }
        });
    }

    private void playNext() {
        if (!filesInFolder.isEmpty()) {
            if(currentIndexAudio<filesInFolder.size()-1){

                currentIndexAudio = currentIndexAudio+1;
                updateIndexLabel();
                isPlaying = false;
                mediaPlayer = null;

            }

        }
    }

    private boolean isSupportedFileType(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".wav") || fileName.endsWith(".mp3");
    }

    // Метод для начала записи аудио
    private void startRecording() {
        audioFormat = new AudioFormat(44100, 16, 2, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            Thread recordingThread = new Thread(() -> {
                AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);
                audioFile = new File(folderPath+"/recording" + fileCounter + ".wav");

                try {
                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            recordingThread.start();
            isRecording = true;
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Метод для открытия аудиофайла
    private void openFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            if (isSupportedFileType(selectedFile)) {
                Media media = new Media(selectedFile.toURI().toString());

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }

                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setOnReady(() -> {
                    primaryStage.setTitle(selectedFile.getName());
                });

                // Сохранение файла в папке "files" с новым именем и добавление его в список
                copyFileToFolder(selectedFile);
            } else {
                // Неподдерживаемый тип файла, выведите сообщение об ошибке
                showAlert("Ошибка", "Неподдерживаемый формат файла: " + selectedFile.getName());
            }
        }
    }


    private void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
        isRecording = false;
        fileCounter++;
        filesInFolder.add(audioFile);
        playNext();
    }

    private void playPrevious() {
        if(currentIndexAudio>0){
            currentIndexAudio = currentIndexAudio-1;
            updateIndexLabel(); // Добавляем обновление метки после изменения currentIndex
        }
        isPlaying = false;
        mediaPlayer = null;
    }

    private void playAudio() {

        selectedFile1=filesInFolder.get(currentIndexAudio);
        Media media = new Media(selectedFile1.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.play();
            if(isClose == false&&filesInFolder.size()>0){
                mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                    if (mediaPlayer != null && mediaPlayer.getTotalDuration() != null && mediaPlayer.getCurrentTime() != null) {
                        double totalDurationMillis = mediaPlayer.getTotalDuration().toMillis();
                        if (totalDurationMillis > 0) {
                            double progress = mediaPlayer.getCurrentTime().toMillis() / totalDurationMillis;
                            timeSlider.setValue(progress * 100);
                        }
                    }
                });

            }
        }
    }

    private void updateIndexLabel() {
        indexLabel.setText("Индекс: " + (currentIndexAudio+1));
    }

    private void copyFileToFolder(File selectedFile) {
        // Генерация нового имени файла на основе порядкового номера
        String fileName = "recording" + fileCounter + selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.'));
        File destination = new File(folderPath+"/" + fileName);
        try {
            java.nio.file.Files.copy(selectedFile.toPath(), destination.toPath());
            filesInFolder.add(destination);
            fileCounter++;
            playNext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFiles() {
        try {

            Runtime.getRuntime().gc();
            // Удаляем файлы
            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile) // Фильтруем только обычные файлы (не каталоги)
                    .map(java.nio.file.Path::toFile)
                    .forEach(File::delete); // Удаляем каждый файл

            filesInFolder.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopAndDisposeMediaPlayer() {
        if (mediaPlayer != null) {
            Runtime.getRuntime().gc();
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null; // Обнуляем ссылку на mediaPlayer
        }
    }

    private void deleteAudio() {
        if(filesInFolder.size()!=0){
            Runtime.getRuntime().gc();
            File currentFile = filesInFolder.get(currentIndexAudio);
            // Удаляем его из списка файлов
            if (selectedFile1 != null) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.dispose(); // Закрываем медиаплеер
                }
                if (selectedFile1.exists()) {
                    if (selectedFile1.delete()) {
                        showAlert("Удалено", "Аудио файл успешно удален!");
                    } else {
                        showAlert("Ошибка", "Не удалось удалить файл!");
                    }
                } else {
                    if (currentFile.delete()) {
                        showAlert("Удалено", "Аудио файл успешно удален!");
                    } else {
                        showAlert("Ошибка", "Не удалось удалить файл!");
                    }
                }

                mediaPlayer = null;
                if(currentIndexAudio>=0){
                    currentIndexAudio = currentIndexAudio-1;
                    if(currentIndexAudio>=0){
                        updateIndexLabel();
                    }
                    if(currentIndexAudio==-1){
                        currentIndexAudio=currentIndexAudio+1;
                    }
                }
                filesInFolder.remove(currentFile);

            }else {
                mediaPlayer = null;
                if(currentIndexAudio>=0){
                    currentIndexAudio = currentIndexAudio-1;
                    if(currentIndexAudio>=0){
                        updateIndexLabel();
                    }
                    if(currentIndexAudio==-1){
                        currentIndexAudio=currentIndexAudio+1;
                    }
                }
                if (currentFile.delete()) {
                    showAlert("Удалено", "Аудио файл успешно удален!");
                } else {
                    showAlert("Ошибка", "Не удалось удалить файл!");
                }
                filesInFolder.remove(currentFile);
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
