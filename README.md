# UTBotJava-action-example

To analyse the Java project with [UTBotJava-action](https://github.com/UnitTestBot/UTBotJava-action) you need to follow these simple steps:

1. Apply the [UTBot gradle plugin](https://plugins.gradle.org/plugin/org.utbot.gradle.plugin) to your project:

```Gradle
plugins {
    id "org.utbot.gradle.plugin" version "1.0.0-alpha"
}
```

2. Create a new file `<your-repository>/.github/workflows/run-utbot-java-action.yml` with a workflow that can be run and configured manually:

```YAML
name: "Run UTBotJava action"

on:
  workflow_dispatch:
    inputs:
      pushTests:
        description: "Push generated tests to the repository"
        type: boolean
        default: true
      generatedTestsRelativeRoot:
        description: "Relative path to the root of the tests"
        type: string
        default: '.utbot/test'
      testFramework:
        type: choice
        options:
          - junit4
          - junit5
          - testng
        default: 'junit5'
      generationTimeout:
        description: "Time budget for one class (ms)"
        type: string
        default: '60000'
      codegenLanguage:
        type: choice
        options:
          - java
          - kotlin
        default: java
      mockStrategy:
        type: choice
        options:
          - 'no-mocks'
          - 'other-packages'
          - 'other-classes'
        default: 'other-packages'
      staticsMocking:
        type: choice
        options: 
          - 'do-not-mock-statics'
          - 'mock-statics'
        default: 'mock-statics'

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - name: Checkout
      uses: actions/checkout@v2

    - name: Setup Java
      uses: actions/setup-java@v2
      with:
        distribution: adopt
        java-version: 8

    - name: Setup Gradle
      uses: gradle/gradle-build-action@v2
      with:
        gradle-version: 6.8

    - name: Run UTBotJava-action
      uses: UnitTestBot/UTBotJava-action@v1
      with:
        pushTests: ${{ inputs.pushTests }}
        generatedTestsRelativeRoot: ${{ inputs.generatedTestsRelativeRoot }}
        testFramework: ${{ inputs.testFramework }}
        generationTimeout: ${{ inputs.generationTimeout }}
        codegenLanguage: ${{ inputs.codegenLanguage }}
        mockStrategy: ${{ inputs.mockStrategy }}
        staticsMocking: ${{ inputs.staticsMocking }}
```

3. On the __Actions__ tab find the section __Run UTBotJava action__:

![image](https://user-images.githubusercontent.com/54814796/177161153-b04709bd-667c-4497-8007-0ac1c37b5e4a.png)

4. Click __Run workflow__ and select the needed options:

![image](https://user-images.githubusercontent.com/54814796/177161568-8d58b093-c927-4a95-86fa-774cb477c379.png)

5. After the workflow is completed, look at the __Security → Code Scanning Alerts__ to find the detected errors (by the way, you won\`t find any if your code is fine 😉):

![image](https://user-images.githubusercontent.com/54814796/177162826-b54c4040-170d-48b9-a660-646ed90a671e.png)

6. Explore any alert by clicking on it:

![image](https://user-images.githubusercontent.com/54814796/177163376-5dffd7eb-be04-4aae-b2f0-cfe86891e662.png)

As you see in this case above, UTBot detected an unchecked ArrayIndexOutOfBoundsException by passing the array `[-192, -192]` to the `isSorted` method.

Click __Show paths__ button, and you see the execution trace.

📍 __Note:__ All these steps are performed more clearly in this project, watch it above. 🙂
