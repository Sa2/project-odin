# sbt-slick-codegen

SQL関連の型をjodaTimeに対応させてDaoを吐かせたいがために無理くり作ったプロジェクト
いつかはslick公式のslick-codegenでjodaTimeに対応させるか、Playに依存しないGeneratorにしたい

## How to run

```
$ ./gen-tables.sh
```
Tables.scalaのコピー先を変更する場合
gen-tables.shの中身を変更する

## Caution

生成時に生成したTables.scalaも実行してしまうためエラーが大量発生することに注意すること

これを回避したい・・・
