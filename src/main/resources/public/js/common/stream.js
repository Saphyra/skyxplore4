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

    this.map = function(mapper){
        const buff = [];

        for(let i in array){
            buff.push(mapper(array[i]));
        }
        return new Stream(buff);
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

    this.sorted = function(comparator){
        array.sort(comparator);
        return this;
    }

    this.toList = function(){
        return array;
    }

    this.toMap = function(keyMapper, valueMapper){
        const result = {};

        this.forEach(function(item){
            result[keyMapper(item)] = valueMapper(item);
        })

        return result;
    }
}

function entryList(map){
    const result = [];
    for(let key in map){
        result.push(new Entry(key, map[key]));
    }
    return result;
}

function orderMapByProperty(map, orderFunction){
    return new Stream(entryList(map))
        .sorted(orderFunction)
        .toMap(function(item){return item.getKey()}, function(item){return item.getValue()});
}

function Entry(k, v){
    const key = k;
    const value = v;

    this.getKey = function(){
        return key;
    }

    this.getValue = function(){
        return value;
    }
}