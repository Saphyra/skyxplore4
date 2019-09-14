function Stream(a){
    const array = a;

    this.allMatch = function(predicate){
        for(let i in array){
            if(!predicate(array[i])){
                return false;
            }
        }

        return true;
    }

    this.noneMatch = function(predicate){
        for(let i in array){
            if(predicate(array[i])){
                return false;
            }
        }

        return true;
    }

    this.peek = function(consumer){
        for(let i in array){
            consumer(array[i]);
        }
        return this;
    }
}