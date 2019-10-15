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

    this.flatMap = function(mappingFunction){
        const newArray = [];

        this.forEach(function(item){
            const res = mappingFunction(item);
            res.forEach(function(r){newArray.push(r)});
        });

        return new Stream(newArray);
    }

    this.forEach = function(consumer){
        for(let i in array){
            consumer(array[i]);
        }
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
        this.forEach(consumer);
        return this;
    }

    this.toMap = function(keyMapper, valueMapper){
        const result = {};

        this.forEach(function(item){
            result[keyMapper(item)] = valueMapper(item);
        })

        return result;
    }
}