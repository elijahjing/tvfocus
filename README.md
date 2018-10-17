# tvfocus
这个android tv端焦点移动框的控件用法：

        final FocusView focusView=new FocusView(this);
        //界面根view
        View  rootView  = findViewById(R.id.rootview);
        rootView.addView(focusView);
        Button button =findViewById(R.id.button);
        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v.hasFocus()){
                    focusView.animate(v);

                }
                
            }
        });

![image](https://github.com/elijahjing/tvfocus/blob/master/app/gif/ezgif-5-6a0801a23dc6.gif )  

