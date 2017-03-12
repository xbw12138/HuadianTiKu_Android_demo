# HuadianTiKu_Android_demo

项目是一个答题的app，安卓端的。
实现了单选题，多选题，判断题的在线答题。

需求者给了一个word文档，就是类似试卷的题库。

我们首先需要手动微处理word文档，
由于文本替换利用记事本很方便，所以没有写代码进行微处理。

处理格式。
danxuan.txt

```
依据华电集团《电力安全生产工作规定》，基层企业应开展班组长安全教育和培训，并做到每<a>B</a>年轮训一遍。
A.半年  B.一年  C.两年  D.三年 |
依据华电集团《电力安全生产工作规定》，二级单位对所属企业正副职领导、正副总工程师、安全生产管理部门负责人，每<a>B</a>年进行一次有关安全生产法规和规程制度的考试
A.半年  B.1年  C. 2年  D.3年 |
依据华电集团《电力生产安全工作规定》，公司、分公司每<a>C</a>年对基层企业组织开展一次安全性评价专家查评（复评）工作。
A. 1年  B. 2年  C. 3年  D. 4年 |
依据华电集团《电力生产安全工作规定》，企业领导应分头定期参加班组安全活动，至少<a>B</a>一次，并做好记录。
A. 每周  B. 每月  C. 每季度  D. 每年  |

。。。。因项目保密中，题库模拟到此
```
duoxuan.txt

```
《安全生产法》规定：<a>ABC</a>应当有注册安全工程师从事安全生产管理工作。鼓励其他生产经营单位聘用注册安全工程师从事安全生产管理工作。注册安全工程师按专业分类管理，具体办法由国务院人力资源和社会保障部门。
A.危险物品生产单位      B.危险物品储存单位
C.矿山、金属冶炼单位    D.危险物品使用单位|
《安全生产法》规定：事故调查处理应当按照科学严谨、<a>BCD</a>的原则，及时、准确地查清事故原因，查明事故性质和责任，总结事故教训，提出整改措施，并对事故责任者提出处理意见。
A.公开    B.依法依规    C.实事求是    D.注重实效|
我国的安全生产工作，强化和落实生产经营单位的主体责任 ,建立<a>ABCD</a>和社会监督的机制。
A.生产经营单位负责    B.职工参与
C.政府监管            D.行业自律 |
<a>ABCD</a>和危险物品的生产、经营、储存单位，应当设置安全生产管理机构或者配备专职安全生产管理人员。
A.矿山    B.金属冶炼    C.建筑施工    D.道路运输单位 |
生产经营项目、场所有多个承包单位、承租单位的，生产经营单位应当<a>ABCD</a>。
A.与承包、承租单位签订专门的安全生产管理协议. 
B.对承包单位、承租单位的安全生产工作统一协调、管理 
C.要求承包单位、承租单位与员工订立劳动合同.
D.要求承包单位、承租单位支付安全生产管理费用 |

。。。。因项目保密中，题库模拟到此
```
panduan.txt

```
《安全生产法》制定的目的是为了加强安全生产工作，杜绝生产安全事故，保障人民群众生命和财产安全，促进经济社会持续健康发展。<a>A</a>|
《安全生产法》规定：矿山、金属冶炼建设项目和用于生产、储存、使用危险物品的建设项目竣工投入生产或者使用前，应当由建设单位负责组织对安全设施进行验收。<a>A</a>|
《特种设备安全法》规定：地方各级人民政府应当建立协调机制，及时协调、解决特种设备安全监督管理中存在的问题。<a>A</a>|
《特种设备安全法》规定：特种设备安全管理人员、检测人员应当按照国家有关规定取得相应资格，方可从事相关工作。<a>A</a>|
《特种设备安全法》规定：特种设备使用单位应当使用取得许可生产并经检验合格的特种设备。禁止使用国家明令淘汰和已经报废的特种设备。<a>B</a>|

。。。。因项目保密中，题库模拟到此

```
微处理到这种程度就可以上传到tiku目录了，只需依次运行 

```
path/danxuan.php
path/duoxuan.php
path/panduan.php
```
这样题库就录入数据库了。
QT.php是客户端随机调用数据库的题目，
比例在db_config.php中
运行了一下发现了判断题的问题，我们没有实现判断题的选项的显示，
所以利用zhuijiapanduan.php增加了题库中判断题的选项。
![image](https://github.com/xbw12138/HuadianTiKu_Android_demo/blob/master/Screenshot/Screenshot_20170312-221301.png)
![image](https://github.com/xbw12138/HuadianTiKu_Android_demo/blob/master/Screenshot/Screenshot_20170312-221318.png)

所以，只要有word题库，我们都可以进行处理，制作在线答题app。
安卓端

```
            <LinearLayout
                <!--四个选项 多选-->
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:id="@+id/mRadioGroupX"
                android:orientation="vertical"
                android:layout_marginTop="10dp"
                android:visibility="gone">
                <RadioButton
                    android:id="@+id/RadioXA"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="A" />

                <RadioButton
                    android:id="@+id/RadioXB"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="B" />

                <RadioButton
                    android:id="@+id/RadioXC"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="C" />

                <RadioButton
                    android:id="@+id/RadioXD"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="D" />

            </LinearLayout>

            <RadioGroup
                android:id="@+id/mRadioGroup"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp">


                <!--四个选项 单选-->

                <RadioButton
                    android:id="@+id/RadioA"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="A" />

                <RadioButton
                    android:id="@+id/RadioB"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="B" />

                <RadioButton
                    android:id="@+id/RadioC"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="C" />

                <RadioButton
                    android:id="@+id/RadioD"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="D" />

                <!--正确答案，默认是隐藏的-->

                <TextView
                    android:visibility="invisible"
                    android:id="@+id/tv_result"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:text="正确答案：A"
                    android:textColor="@color/colorPrimaryDark"
                    android:textSize="18sp" />

            </RadioGroup>
```
原生RadioGroup控件只能实现单选，
所以我们利用单个RadioButton存放在一个LinearLayout中，实现多选。

Bug：单选，多选，判断题型之间的逻辑切换处理的极其不优雅，需要大神fork，非常感谢！！！


