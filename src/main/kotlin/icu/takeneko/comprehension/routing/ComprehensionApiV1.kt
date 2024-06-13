package icu.takeneko.comprehension.routing

import io.ktor.resources.*

@Resource("/api/v1")
class ComprehensionApiV1 {
    @Resource("passcode")
    class Passcode(val passcode: String? = null) {
        @Resource("validate")
        class Validate(val parent: Passcode)

        @Resource("info")
        class Info(val parent: Passcode)

        @Resource("start")
        class Start(val parent: Passcode)

        @Resource("terminate")
        class Terminate(val parent: Passcode)
    }

    @Resource("/test")
    class Test(val passcode: String) {
        @Resource("/commit")
        class Commit(val parent: Test)

        @Resource("/commit")
        class Answer(val parent: Test)
    }

    @Resource("/intrinsics")
    class Intrinsics(val auth: String?) {
        @Resource("/passcode")
        class Passcode(val parent: Intrinsics){
            @Resource("/create")
            class Create(val parent: Passcode, val passcode: String)

            @Resource("/delete")
            class Delete(val parent: Passcode, val passcode: String)

            @Resource("/results")
            class Results(val parent: Passcode, val passcode: String)
        }

        @Resource("/upload")
        class Upload(val parent: Intrinsics)

        @Resource("/list")
        class List(val parent: Intrinsics)

        @Resource("/delete")
        class Delete(val parent: Intrinsics, val name: String)

        @Resource("/reload")
        class Reload(val parent: Intrinsics)
    }
}