## app-check
タイプ変更とか種別変更のチェックドメイン見てたら、似たコードが多い上に１つの修正が何カ所にも必要になりそうな設計だったのでやりなおす

Lazy を導入したときはテクさを上げない方針にしたが、自重せずに重複コードを消してモジュール化を意識した設計にしたらどうなるかを確かめる

### 気にすること
+ 経路の差異をどうするか
  + とりあえずドメインで表現する
+ 似たチェックを流用したい
  + 本質的にはこっちをやりたい
  + タイプ変更でも種別変更でも同じ部品を使える
  + ただし Policy が総合して何をチェックしているかは１クラスで一瞥したい
+ ドメインはツリー構造だとして、チェックは親ドメインが通った場合のみ子ドメインに続く
+ 同じエンティティに対するチェックに順番は設けない
  + 順番（早期リターン）はモジュール化しづらいので
  + 順番を排除するために Either ではなく Validation で全部チェックする
+ 初設計時はモジュール化をすると Result enum が組み立てられないという話をしたけど、Policy ごとに専用 Result enum は設けない方に倒してみる

### domain
user -> contract -> voice (if exists)

ただし user 参照は遅いため評価は遅延にしたい  
また voice は contract が不正な場合は参照エラーになるので遅延する任意エンティティとなる

#### package
+ core に identity や共通 attributes を置く
+ uc に policy を置く
+ check に共通 checker と error を置く
  + どの uc もここに依存する

### check
user

channel     | user status | payment method  
:--         | :--         | :--             
web         | using only  | credit card only
call center | using only  |                 

contract

channel     | contract type   | contract option      
:--         | :--             | :--                  
web         | individual only | no option only
call center | individual only |                      

voice

channel     | voice type | stopping         | voice option
:--         | :--        | :--              | :--         
web         | main only  | un stopping only |             
call center | main only  | un stopping only | no options  

date

channel     | date        
:--         | :--         
web         |             
call center | 9:00 - 21:00

### model
![スクリーンショット 2019-06-28 0 58 35](https://user-images.githubusercontent.com/18749992/60281436-7aba9080-98f4-11e9-8a6d-1e1e3d138acf.png)

### 感想
+ ドメイン層が定義的になった
+ というか static ばっかだし
+ WebTypeChangeAppCheckPolicy は一瞥しやすい
+ 逆に ValidationUtil は多少やり過ぎた感
+ Value 便利
+ 途中で飽きた
+ 脳内設計 -> 実装 -> テスト -> 整理絵　で 3.5h くらい
+ 飲みながらだったので雑
  + 続きをやる気になるかは謎
