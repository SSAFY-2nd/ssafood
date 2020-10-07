from flask import Flask, jsonify, request
import user_based
import tf_idf
from flask_cors import CORS
 
app = Flask (__name__)
CORS(app)

@app.route('/getSimilerReview/')
def get_TF_IDF_store():
    store_id = request.args.get('store_id', "149")
    data = tf_idf.getStore(int(store_id))
    print(data)
    return jsonify(data)

@app.route('/getUserBased/')
def get_user_based_store():
    user_id = request.args.get('user_id', "68632")
    data = user_based.get10store(int(user_id))
    print(data)
    return jsonify(data)


#def hello_world():
#    #print(user_based.get10store(345))
#    user_id = request.args.get('user_id', "345")
#    data = user_based.get10store(user_id)
#    ret = []
#    for i in data.index:
#        tmp = {}
#        tmp['store_id'] = i[0]
#        tmp['name'] = i[1]
#        tmp['score'] = data['score'][i]
#        ret.append(tmp)
#    print(ret)
#    return jsonify(ret)
 
if __name__ == "__main__":
    app.run()
