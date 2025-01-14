public class Muscle {
    private String OldVersionKorean;
    private String NewVersionKorean;
    private String EnglishAnswer;
    private String image;
    // 근육 이름 세 가지와 이미지 경로

    public Muscle(String OldVersionKorean, String NewVersionKorean, String EnglishAnswer, String image) {
        this.OldVersionKorean = OldVersionKorean;
        this.NewVersionKorean = NewVersionKorean;
        this.EnglishAnswer = EnglishAnswer;
        this.image = image;
    }

    public String getOldVersionKorean() {
        return this.OldVersionKorean;
    }

    public String getNewVersionKorean() {
        return this.NewVersionKorean;
    }

    public String getEnglishAnswer() {
        return this.EnglishAnswer;
    }

    public String getImage() {
        return this.image;
    }    
}
