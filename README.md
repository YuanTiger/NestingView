#简介
本项目包含了一些ViewGroup的嵌套使用，包括##RecyclerView嵌套RecylcerView,ScrollView嵌套ListView##,希望可以给大家一些灵感

#原理
###RecyclerView嵌套RecylcerView
RecyclerView嵌套RecyclerView的条目，项目中可能会经常有这样的需求，但是我们将子条目设置为RecyclerView之后，却显示不出来。自己试了很久，终于找到了原因：**必须先设置子RecylcerView的高度**。你要花精力确定出子RecyclerView里面条目的高度，然后从而确定子RecyclerView的高度，设置给子RecylcerView，这样做RecyclerView就可以正确显示出子ReclyclerView。

###ScrollView嵌套ListView
这个的问题在于滚动事件的冲突，那么我们关注的点就是**down事件发生在ListView的时候先把事件拦截下来,如果ListView需要则不向上传递到ScrollView，如果ListView不需要则放行事件**




>有任何问题都可以联系我：mengyuanzz@126.com