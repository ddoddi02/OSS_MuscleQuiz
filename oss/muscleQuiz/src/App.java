public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }

    // 일단 생각해야되는것 :
    // 어떻게 랜덤으로 생성할 것인가?
    // >> JSON 파일에 번호를 붙여서 맵에 모두 넣어놓고 랜덤 번호로 찾아서 생성?
    // 어떻게 정답을 비교할 것인가?
    // >> 요소 하나씩 비교하면 됨
    // 정답을 새로운 한글, 구 한글, 영어를 모두 넣어야 하는데 그 방법
    // >> 리스트로 만들어서 순회하며 비교

    // 일단 제이슨으로 만들고
    // answerList 에 KoOldAnswer, KoNewAnswer, EnAnswer 3가지를 모두 집어넣는다. > 그냥 문자열을 3개 만들어버리는게 편할듯
    // 그리고 이미지도 문자열로 저장 (Muscle 클래스에)
    // 숫자도 전부 지정하고 맵에 숫자, muscle 형태로 추가
    // 랜덤 번호를 키로 해서 찾고 muscle 을 받아서 list 순회 비교 (getlist 로 하면 되겠지?)

}
