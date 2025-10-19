package calculator

fun main() {
   println("덧셈할 문자열을 입력해 주세요.")
    val input = readlnOrNull() ?: ""

    try {
        val result = add(input)
        println("결과 : $result")
    } catch (_: IllegalArgumentException) {
        return
    }

}

/* 기본 덧셈 기능 */
fun add(input: String): Int {
    // 빈 문자열 : 0 반환
    if (input.isBlank()) return 0
    // 구분자, 숫자인 문자열 찾기
    val (delimiter, numberString) = parser(input)
    // parts : 구분자 구별해서 만든 리스트
    val parts = numberString.split(delimiter)
    // numbers : 빈 리스트 만들기
    val numbers = mutableListOf<Int>()


    // 예외 검사
    for (part in parts) {
        if (part.isBlank()) continue

        val num = noNumbers(part)     // 숫자 변환 (아니면 예외)
        negativeNumbers(num)          // 음수 검사 (음수면 예외)

        numbers.add(num) // 예외 검사 완료시 add 실행
    }

    // 합 계산
    var sum = 0
    for (n in numbers) {
        sum += n
    }
    return sum
}

/* 기본 + 커스텀 구분자 구별 파서 함수 */
private fun parser(input: String): Pair<String, String> {
    // 만약 "//" 이거로 시작하면
    if(input.startsWith("//")){
        // 뒤에 \n 기점으로 나누기
        val normalized = input.replace("\\n", "\n")
        val split = normalized.split("\n", limit = 2)
        // 앞에는 구분자 정보
        val delimiterPart = split[0]
        // 뒤에는 문자열 + 숫자인 문자열 정보
        val numbersPart  = split[1]

        // 구분자 == // 빼고
        val delimiter = delimiterPart.substring(2)
        // (구분자 + 숫자 문자열) 반환
        return Pair(delimiter, numbersPart)
    }
    else{ // 기본 구분자
        val replaced = input.replace(":", ",")
        return Pair(",", replaced)
    }
}

/* 예외 처리 */
private fun noNumbers(str: String): Int {
    return str.toIntOrNull() ?: throw IllegalArgumentException()
}

private fun negativeNumbers(num: Int) {
    if (num < 0) throw IllegalArgumentException()
}