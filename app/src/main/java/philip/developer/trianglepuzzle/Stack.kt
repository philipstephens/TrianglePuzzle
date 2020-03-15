package developer.philip.trianglepuzzle

class Stack(){
    var backingList : MutableList<Any> = arrayListOf()

    fun push(element:Any){
        backingList.add(element)
    }

    fun pop():Any?{
        if (backingList.isEmpty()){
            return null
        }
        val value = backingList.last()
        backingList.removeAt(backingList.size - 1)
        return value
    }

    fun peek():Any?{
        return backingList.lastOrNull()
    }

    fun size():Int{
        return backingList.size
    }

    fun isEmpty():Boolean{
        return backingList.isEmpty()
    }
}