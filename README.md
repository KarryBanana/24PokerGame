# 24PokerGame
2019 Summer Semester Java Project
# 项目介绍

**24点算术———24-point arithmetic**
这是一个基于Java语言编写的24点算术游戏.
玩家在除去大、小王的扑克牌中，抽取4个数通过四则运算、括号得到24.

## 程序运行

电脑需安装支持Java语言的IDE软件，例如Eclipse，Intellij IDEA
Eclipse下载地址：https://www.eclipse.org/downloads/
Intellij IDEA下载地址：https://www.jetbrains.com/idea/

## 使用指南

Next Question按键：显示下一组题目.
Solution按键：程序显示答案（有解显示答案表达式，无解显示"No Solution"）.
Your Answer栏：玩家填写自己的答案.
Submit按键：玩家提交自己的答案,提交后程序会显示提交的答案正确与否.

## 基本原理

### 整体思路

设计程序界面Frame框架--->添加界面面板--->添加按钮Submit、 Solution、 Next等按键--->实现不同按键的具体功能.

### 具体功能实现

Solution按键:寻找答案，枚举所有情况，四个数字之间放置的符号共4*4*4=64种情况,运算顺序6种，所以总共256*6=1536种.
Submit按键：判断玩家答案是否正确，玩家输入式子为中缀表达式——>后缀表达式———>计算表达式的值(栈).
另外检查表达式中的数字是否是扑克牌对应的4个数字.
程序界面:利用Java自带的Swing类设计GUI界面，绘制扑克牌.

## 待改进之处

1.存在Solution有多组解,程序只显示了一组解.
2.难度随机，应设计递增的难度.
3.界面不大美观。。。

## 常见问题

背景图片无法显示：程序第78行，图片路径需要修改成当前图片所处的绝对路径.
扑克牌图片无法显示：程序第555行，图片路径修改为相对路径.
