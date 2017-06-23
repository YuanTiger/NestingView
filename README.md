# 简介
本项目包含了一些ViewGroup的嵌套使用，包括**RecyclerView嵌套RecylcerView,ScrollView嵌套ListView**,希望可以给大家一些灵感

# 原理
### RecyclerView嵌套RecylcerView
RecyclerView嵌套RecyclerView的条目，项目中可能会经常有这样的需求，现在已无需先计算子RecyclerView的高度，简单实用。

### ScrollView嵌套ListView
这个的问题在于滚动事件的冲突，那么我们关注的点就是**down事件发生在ListView的时候先把事件拦截下来,如果ListView需要则不向上传递到ScrollView，如果ListView不需要则放行事件**




>有任何问题都可以联系我：mengyuanzz@126.com
