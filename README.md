# RhythmCatchball
2019 1학기 소프트웨어공학 팀 프로젝트 과제입니다. 

프로젝트 명: RhythmCatchball

팀원: 김민종 박성민 임석렬 김범준

## 게임소개

두명에서 리듬에 맞춰 공을 주고받는 게임입니다.

박자를 정확히 맞춰 높은 점수를 받아봅시다!

## 설치방법
```
~~나중에 완성 후 추가~~
```


## 게임 가이드
### 1. 조작법
#### 1.1 멀티 플레이

1p의 기본 키는 : (공던지기) a s d (공받기) space

2p의 기본 키는 : (공던지기) ← ↑ → (공받기) shift


공은 세 박자로 던질 수 있습니다

![공여러개던짐](https://user-images.githubusercontent.com/50068946/59751333-4a9f3c00-92bb-11e9-83a0-1dcb31546fd4.gif)

가장 느리게 던지기

  기본설정: (a →) 키


보통속도로 던지기

  기본설정: (s ↑) 키

빠르게 던지기

  기본설정: (d ←) 키

space를 눌러서 공을 잡을 수 있습니다



#### 1.2. 싱글 플레이
기본적으로 (공던지기) asd (공받기) space로 키가 설정되어 있고

멀티플레이와 조작 방법은 동일함니다. 

싱글플레이로 플레이시 AI와 플레이합니다. 





### 2. 게임 플레이
![start](https://user-images.githubusercontent.com/50068946/59751330-4a06a580-92bb-11e9-88ae-3d641ef0d80e.gif)

게임은 60초동안 플레이 할 수 있습니다

60초 안에 상대방에게 공을 던지고 공을 받으면서 높은 점수를 받는 사람이 승리합니다

처음 공은 ....~~~~~~~ 시작할 때 공 주는 공식 

```
공 던지는 이미지
```
공이 있으면 박자에 맞춰 던질 수 있습니다. 

던지는 방법은 세가지 입니다

![throw](https://user-images.githubusercontent.com/50068946/59751331-4a06a580-92bb-11e9-9252-5ffeadc8bc94.gif)

느리게 던지기

중간속도로 던지기

빠르게 던지기


![receive](https://user-images.githubusercontent.com/50068946/59751329-496e0f00-92bb-11e9-878c-78837f3af2a6.gif)

박자에 맞춰 공을 받으면 됩니다

판정은 4가지가 있습니다

![check_exactly](https://user-images.githubusercontent.com/50068946/59751325-496e0f00-92bb-11e9-8b1c-6cfc7a579354.png)
EXACTLY

![check_neat](https://user-images.githubusercontent.com/50068946/59751327-496e0f00-92bb-11e9-8e37-f9044444e2e4.png)
NEAT

![check_cool](https://user-images.githubusercontent.com/50068946/59751324-48d57880-92bb-11e9-99fa-e85b518f43f3.png)
COOL

![check_lame](https://user-images.githubusercontent.com/50068946/59751326-496e0f00-92bb-11e9-98c9-c50b4489fd74.png)
LAME


박자를 얼마나 정확히 맞췄냐에 따라 점수를 받습니다

만약 공을 놓쳤을 시 LAME을 받고 점수가 조금 깎이고 공은 상대방에게로 돌아가니다 (ㅁ맞나요?????)

![여러개받음](https://user-images.githubusercontent.com/50068946/59751336-4b37d280-92bb-11e9-94dd-cf7b59dfc6c6.gif)

공이 여러개가 동시에 오는 경우

처음 누른 박자에 맞춰 점수가 판정됩니다

그 후 나머지 공은 처음 공의 점수와 동일하게 받습니다. 


### 3. 환경설정
![메인화면](https://user-images.githubusercontent.com/50068946/59751335-4a9f3c00-92bb-11e9-9b4f-98988dc89903.PNG)

환경설정은 메인화면에 있습니다


![키설정](https://user-images.githubusercontent.com/50068946/59751338-4b37d280-92bb-11e9-92c1-0372ec55ea36.PNG)


여기서 1p와 2p의 키를 변경하고

음향과 해상도도 설정할 수 있습니다. 













